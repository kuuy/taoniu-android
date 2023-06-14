package com.kuuy.taoniu.data

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.account.dto.TokenDto
import com.kuuy.taoniu.di.NetworkModule
import com.kuuy.taoniu.di.PreferencesModule
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.internal.closeQuietly
import timber.log.Timber
import java.io.IOException
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.resumeWithException

@Singleton
class AuthToken constructor(
  @Named(NetworkModule.HTTP_CLIENT) private var okHttpClient: OkHttpClient,
  @Named(PreferencesModule.AUTH_PREFERENCES) private var authPreferences: SharedPreferences,
) {
  private var accessToken: String? = null

  fun accessToken(): String? {
    if (accessToken == null) {
      accessToken = authPreferences.getString("ACCESS_TOKEN", null)
    }
    return accessToken
  }

  fun shouldRefresh(): Boolean {
    val now = System.currentTimeMillis()
    val refreshAt = authPreferences.getLong("REFRESH_AT", 0L)
    if (now > refreshAt) {
      return true
    }
    return false
  }

  suspend fun refreshToken() {
    val refreshToken = authPreferences.getString("REFRESH_TOKEN", null)
      ?: throw Error("refresh token is not available")

    var request = Request.Builder()
      .header("Content-Type", "application/x-www-form-urlencoded")
      .url(BuildConfig.ACCOUNT_API_URL + "v1/token/refresh")
      .post(FormBody.Builder().add("refresh_token", refreshToken).build())
      .build()

    return suspendCancellableCoroutine {
      val callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
          Timber.tag(TAG).d("refresh token call failed. $e")
          it.resumeWithException(Error("refresh token call failed. $e"))
        }

        override fun onResponse(call: Call, response: Response) {
          if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            var result = gson.fromJson(response.body?.string(), DtoResponse::class.java)
            if (result.success) {
              var token = gson.fromJson(gson.toJson(result.data), TokenDto::class.java)
              accessToken = token.access
              authPreferences.edit()
                .putString("ACCESS_TOKEN", accessToken)
                .putLong("REFRESH_AT", System.currentTimeMillis() + 895000)
                .apply()
              Timber.tag(TAG).d("refresh token success!")
            } else {
              it.resumeWithException(Error("refresh token call failed. ${result.data.toString()}"))
            }
          } else {
            if (response.code == 401 || response.code == 403) {
              clearToken()
            }
            it.resumeWithException(Error("refresh token call failed. ${response.code}"))
          }
          response.closeQuietly()
        }
      }
      val call = okHttpClient.newCall(request)
      it.invokeOnCancellation { call.cancel() }
      call.enqueue(callback)
    }
  }

  fun clearToken() {
    accessToken = null
    authPreferences.edit()
      .clear()
      .remove(ACCESS_TOKEN)
      .remove(REFRESH_TOKEN)
      .remove(REFRESH_AT)
      .apply()
  }

  companion object {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    private const val REFRESH_AT = "REFRESH_AT"
    private const val TAG = "AUTH_TOKEN"
  }
}
