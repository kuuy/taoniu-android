package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.TickersApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TickersResource @Inject constructor(
  private var tickersApi: TickersApi,
  private var gson: Gson,
) {
  suspend fun gets(
    symbols: List<String>,
    fields: List<String>,
  ) : Flow<ApiResponse<List<String>>> {
    return flow {
      val response = tickersApi.gets(symbols.joinToString(","), fields.joinToString(","))
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
    }.flowOn(Dispatchers.IO)
  }
}