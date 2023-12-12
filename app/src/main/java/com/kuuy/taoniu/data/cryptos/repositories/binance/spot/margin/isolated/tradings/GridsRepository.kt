package com.kuuy.taoniu.data.cryptos.repositories.binance.spot.margin.isolated.tradings

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.isolated.tradings.GridInfoDto
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.margin.isolated.tradings.fishers.GridsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GridsRepository @Inject constructor(
  private val resource: GridsResource
) {
  suspend fun listings(
    symbols: List<String>,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResource<DtoPaginate<GridInfoDto>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.listings(symbols, current, pageSize).firstOrNull()) {
        is ApiResponse.Success -> {
          emit(ApiResource.Success(response.data))
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