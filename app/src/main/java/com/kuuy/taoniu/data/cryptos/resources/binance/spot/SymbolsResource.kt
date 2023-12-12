package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.SymbolsApi
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.SymbolInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SymbolsResource @Inject constructor(
  private var symbolsApi: SymbolsApi
) {
  suspend fun get(
    symbol: String,
  ) : Flow<ApiResponse<SymbolInfoDto>> {
    return flow {
      val response = symbolsApi.get(symbol)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        var apiError = ApiError(
          response.code(),
          response.message(),
        )
        response.errorBody()?.let {
          apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
        }
        emit(ApiResponse.Error(apiError))
      }
    }.flowOn(Dispatchers.IO)
  }

  suspend fun gets(
    symbols: List<String>,
  ) : Flow<ApiResponse<List<String>>> {
    return flow {
      val response = symbolsApi.gets(symbols.joinToString(","))
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        var apiError = ApiError(
          response.code(),
          response.message(),
        )
        response.errorBody()?.let {
          apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
        }
        emit(ApiResponse.Error(apiError))
      }
    }.flowOn(Dispatchers.IO)
  }

  suspend fun scan() : Flow<ApiResponse<List<String>>> {
    return flow {
      val response = symbolsApi.scan()
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        var apiError = ApiError(
          response.code(),
          response.message(),
        )
        response.errorBody()?.let {
          apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
        }
        emit(ApiResponse.Error(apiError))
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }
}