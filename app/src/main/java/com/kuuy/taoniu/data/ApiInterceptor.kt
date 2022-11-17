package com.kuuy.taoniu.data

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.account.dto.TokenDto
import com.kuuy.taoniu.data.account.repositories.TokenRepository
import com.kuuy.taoniu.di.PreferencesModule
import okhttp3.*
import java.util.concurrent.TimeUnit
import javax.inject.Named

class ApiInterceptor constructor(
  @Named(PreferencesModule.AUTH_PREFERENCES) private var authPreferences: SharedPreferences
) : Interceptor {
  var repository: TokenRepository? = null

  override fun intercept(chain: Interceptor.Chain): Response {
    var accessToken = authPreferences.getString("ACCESS_TOKEN", "")

    val original = chain.request()
    val request = original.newBuilder()
      .addHeader("Authorization", "Taoniu $accessToken")
      .build()

    var response = chain.proceed(request)
    if (response.code == 401) {
      accessToken = refreshToken()
      if (accessToken != "") {
        authPreferences.edit().putString("ACCESS_TOKEN", accessToken)
        response.close()
        val request = original.newBuilder()
          .addHeader("Authorization", "Taoniu $accessToken")
          .build()
        return chain.proceed(request)
      }
    }
    return response
  }

  fun refreshToken(): String {
    val client = OkHttpClient.Builder()
      .readTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(20, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .build()

    val refreshToken = authPreferences.getString("REFRESH_TOKEN", "")!!
    var request = Request.Builder()
      .header("Content-Type", "application/x-www-form-urlencoded")
      .url(BuildConfig.ACCOUNT_API_URL + "v1/token/refresh")
      .post(FormBody.Builder().add("refresh_token", refreshToken).build())
      .build()

    val response = client.newCall(request).execute()
    if (response.code == 401) {
      authPreferences.edit().apply{
        remove("ACCESS_TOKEN")
        remove("REFRESH_TOKEN")
      }.commit()
    }
    if (response.code == 200) {
      try {
        val gson = GsonBuilder().create()
        var result = gson.fromJson(response.body?.string(), DtoResponse::class.java)
        if (result.success) {
          var token = gson.fromJson(gson.toJson(result.data), TokenDto::class.java)
          return token.access
        }
      } catch (e: JsonSyntaxException) {
      } catch (e: NullPointerException) {
      }
    }

    return ""
  }
}