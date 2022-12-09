package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.KlinesResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KlinesRepository @Inject constructor(
  private val resource: KlinesResource
) {
  suspend fun series(
    symbol: String,
    interval: String,
    limit: Int,
  ) : Flow<ApiResource<DtoResponse<List<FloatArray>>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.series(symbol, interval, limit).first()) {
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