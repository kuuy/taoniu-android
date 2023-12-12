package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.TradingsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.EOFException
import javax.inject.Inject

class TradingsResource @Inject constructor(
  private var tradingsApi: TradingsApi
) {
  suspend fun scan() : Flow<ApiResponse<List<String>>> {
    return flow {
      val response = tradingsApi.scan()
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        try {
          response.errorBody()?.let {
            var apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
            emit(ApiResponse.Error(apiError))
          }
        } catch (e: Throwable) {}
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }
}