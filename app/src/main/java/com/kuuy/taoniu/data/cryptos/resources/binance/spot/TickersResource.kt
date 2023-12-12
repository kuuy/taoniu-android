package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.TickersApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TickersResource @Inject constructor(
  private var tickersApi: TickersApi
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
        var apiError = ApiError(
          response.code(),
          response.message(),
        )
        try {
          response.errorBody()?.let {
            apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
          }
        } catch (ioException: JsonIOException) {
        } catch (syntaxException: JsonSyntaxException) {
        }
        emit(ApiResponse.Error(apiError))
      }
    }.flowOn(Dispatchers.IO)
  }
}