package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.TradingsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TradingsRepository @Inject constructor(
  private val resource: TradingsResource
) {
  suspend fun scan() : Flow<ApiResource<List<String>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.scan().first()) {
        is ApiResponse.Success -> {
          emit(ApiResource.Success(response.data))
        }
        is ApiResponse.Empty -> {
          emit(ApiResource.Success(null))
        }
        is ApiResponse.Error -> {
          emit(ApiResource.Error(response.apiError))
        }
      }
    }
  }
}