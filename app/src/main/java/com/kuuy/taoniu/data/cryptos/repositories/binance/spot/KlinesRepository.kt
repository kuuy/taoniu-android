package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.KlinesResource

class KlinesRepository @Inject constructor(
  private val resource: KlinesResource
) {
  suspend fun series(
    symbol: String,
    interval: String,
    limit: Int,
  ) : Flow<ApiResource<List<FloatArray>>> {
    return flow {
      resource.series(symbol, interval, limit).onStart {
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