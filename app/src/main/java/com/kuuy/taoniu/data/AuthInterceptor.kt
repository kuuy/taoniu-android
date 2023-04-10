package com.kuuy.taoniu.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class AuthInterceptor constructor(
  private val authToken: AuthToken,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request().newBuilder()
      .also { request ->
        authToken.accessToken()?.let {
          request.header("Authorization", "Taoniu $it")
        }
      }
      .build()
    return chain.proceed(request)
  }
}