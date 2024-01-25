package com.kuuy.taoniu.data.cryptos.repositories.binance.spot.analysis.margin.isolated.tradings.fishers

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.margin.isolated.tradings.fishers.GridInfoDto
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.analysis.margin.isolated.tradings.fishers.GridsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GridsRepository @Inject constructor(
  private val resource: GridsResource,
) {
  suspend fun listings(
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResource<DtoPaginate<GridInfoDto>>> {
    return flow {
      resource.listings(current, pageSize).onStart {
        emit(ApiResource.Loading())
      }.catch {
        emit(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResponse.Success -> {
            val data = response.data
            emit(ApiResource.Success(data))
          }
          is ApiResponse.Error -> {
            emit(ApiResource.Error(response.apiError))
          }
          is ApiResponse.Empty -> {
            emit(ApiResource.Success(null))
          }
        }
      }
    }
  }

  suspend fun series(
    limit: Int,
  ) : Flow<ApiResource<List<ArrayList<Any>>>> {
    return flow {
      resource.series(limit).onStart {
        emit(ApiResource.Loading())
      }.catch {
        emit(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResponse.Success -> {
            val data = response.data
            emit(ApiResource.Success(data))
          }
          is ApiResponse.Error -> {
            emit(ApiResource.Error(response.apiError))
          }
          is ApiResponse.Empty -> {
            emit(ApiResource.Success(null))
          }
        }
      }
    }
  }
}