package com.kuuy.taoniu.data.cryptos.repositories.binance.spot.plans

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans.DailyInfoDto
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.plans.DailyResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DailyRepository @Inject constructor(
  private val resource: DailyResource
) {
  suspend fun listings(
    symbols: List<String>,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResource<DtoPaginate<DailyInfoDto>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.listings(symbols, current, pageSize).first()) {
        is ApiResponse.Success -> {
          emit(ApiResource.Success(response.data))
        }
        is ApiResponse.Empty -> {
          emit(ApiResource.Success(null))
        }
        is ApiResponse.Error -> {
          emit(ApiResource.Error(response.errorMessage))
        }
      }
    }
  }
}