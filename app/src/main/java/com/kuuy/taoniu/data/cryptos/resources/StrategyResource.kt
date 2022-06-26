package com.kuuy.taoniu.data.cryptos.resources

import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.kuuy.taoniu.data.cryptos.api.StrategyApi
import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto
import com.kuuy.taoniu.data.ApiResponse

class StrategyResource @Inject constructor(
  private var strategyApi: StrategyApi 
) {
  suspend fun getStrategyListings()
      : Flow<ApiResponse<StrategyListingsDto>> {
    return flow {
      val response = strategyApi.getStrategyListings()
      emit(ApiResponse.Success(response))
    }.flowOn(Dispatchers.IO)
  }
}
