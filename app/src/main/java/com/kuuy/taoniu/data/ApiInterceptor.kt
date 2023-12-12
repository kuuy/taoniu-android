package com.kuuy.taoniu.data

import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Singleton

@Singleton
class ApiInterceptor constructor(
  private val authToken: AuthToken,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response = runBlocking{
    val request = chain.request()
    if (authToken.accessToken() != null && authToken.shouldRefresh()) {
      authToken.refresh()
    }
    chain.proceed(request)
  }

  companion object {
    private const val TAG = "API_INTERCEPTOR"
  }
}