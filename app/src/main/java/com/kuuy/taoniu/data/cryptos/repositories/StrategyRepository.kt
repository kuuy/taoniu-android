package com.kuuy.taoniu.data.cryptos.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

import com.kuuy.taoniu.data.cryptos.resources.StrategyResource
import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse

class StrategyRepository @Inject constructor(
  private val strategyResource: StrategyResource
) {
  suspend fun getStrategyListings()
      : Flow<ApiResource<StrategyListingsDto>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = strategyResource.getStrategyListings(1, 25).firstOrNull()) {
        is ApiResponse.Success -> {
          val data = response.data
          emit(ApiResource.Success(data))
        }
        is ApiResponse.Error -> {
          emit(ApiResource.Error(response.apiError))
        }
        else -> {
          emit(ApiResource.Success(null))
        }
      }
    }
  }
}

