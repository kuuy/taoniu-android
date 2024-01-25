package com.kuuy.taoniu.data.cryptos.resources

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.kuuy.taoniu.data.cryptos.api.StrategyApi
import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto
import com.kuuy.taoniu.data.ApiResponse

class StrategyResource @Inject constructor(
  private var strategyApi: StrategyApi,
  private var gson: Gson,
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
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.flowOn(Dispatchers.IO)
  }
}
