package com.kuuy.taoniu.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.account.dto.TokenDto
import com.kuuy.taoniu.di.PreferencesModule
import okhttp3.*
import okhttp3.internal.wait
import java.util.concurrent.TimeUnit
import javax.inject.Named

class ApiInterceptor constructor(
  @Named(PreferencesModule.AUTH_PREFERENCES) private var authPreferences: SharedPreferences
) : Interceptor {
  private var accessToken: String = ""

  override fun intercept(chain: Interceptor.Chain): Response {
    if (accessToken.isEmpty()) {
      accessToken = authPreferences.getString("ACCESS_TOKEN", "")!!
    }

    val original = chain.request()
    val builder = original.newBuilder()
    if (accessToken.isNotEmpty()) {
      builder.header("Authorization", "Taoniu $accessToken")
    }
    val response = chain.proceed(builder.build())
    if (response.code == 401) {
      refreshToken()
      if (accessToken.isNotEmpty()) {
        response.close()
        val retry = original.newBuilder()
          .addHeader("Authorization", "Taoniu $accessToken")
          .build()
        return chain.proceed(retry)
      }
    }

    return response
  }

  @SuppressLint("CommitPrefEdits")
  fun refreshToken() {
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
      }.apply()
    }
    if (response.code == 200) {
      try {
        val gson = GsonBuilder().create()
        var result = gson.fromJson(response.body?.string(), DtoResponse::class.java)
        if (result.success) {
          var token = gson.fromJson(gson.toJson(result.data), TokenDto::class.java)
          accessToken = token.access
          authPreferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
        }
      } catch (t: Throwable) { }
    }
  }
}