package com.kuuy.taoniu.data.cryptos.resources.currencies

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.cryptos.api.currencies.AboutApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AboutResource @Inject constructor(
  private var aboutApi: AboutApi,
  private var gson: Gson,
) {
  suspend fun get(
    symbol: String,
  ): Flow<ApiResponse<String>> {
    return flow {
      val response = aboutApi.get(symbol)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }
}