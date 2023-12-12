package com.kuuy.taoniu.data.cryptos.resources

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.kuuy.taoniu.data.ApiError
import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.kuuy.taoniu.data.cryptos.api.StrategyApi
import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto
import com.kuuy.taoniu.data.ApiResponse
import java.io.EOFException

class StrategyResource @Inject constructor(
  private var strategyApi: StrategyApi 
) {
  suspend fun getStrategyListings(current: Int, pageSize: Int)
      : Flow<ApiResponse<StrategyListingsDto>> {
    return flow {
      val response = strategyApi.getStrategyListings(current, pageSize)
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
    }.flowOn(Dispatchers.IO)
  }
}
