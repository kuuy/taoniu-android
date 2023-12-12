package com.kuuy.taoniu.data.cryptos.repositories.binance.spot.analysis.margin.isolated.tradings.fishers

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.margin.isolated.tradings.fishers.GridInfoDto
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.analysis.margin.isolated.tradings.fishers.GridsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GridsRepository @Inject constructor(
  private val resource: GridsResource,
) {
  suspend fun listings(
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResource<DtoPaginate<GridInfoDto>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.listings(current, pageSize).firstOrNull()) {
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

  suspend fun series(
    limit: Int,
  ) : Flow<ApiResource<List<ArrayList<Any>>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.series(limit).firstOrNull()) {
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