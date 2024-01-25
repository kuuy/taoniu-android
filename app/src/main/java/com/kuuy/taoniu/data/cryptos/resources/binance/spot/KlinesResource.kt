package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.KlinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KlinesResource @Inject constructor(
  private var klinesApi: KlinesApi,
  private var gson: Gson,
) {
  suspend fun series(
    symbol: String,
    interval: String,
    limit: Int,
  ) : Flow<ApiResponse<List<FloatArray>>> {
    return flow {
      val response = klinesApi.series(symbol, interval, limit)
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