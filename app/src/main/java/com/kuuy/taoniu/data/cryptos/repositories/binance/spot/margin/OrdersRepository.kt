package com.kuuy.taoniu.data.cryptos.repositories.binance.spot.margin

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.OrderInfoDto
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.margin.OrdersResource

class OrdersRepository @Inject constructor(
  private val resource: OrdersResource
) {
  suspend fun listings(
    symbols: List<String>,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResource<DtoPaginate<OrderInfoDto>>> {
    return flow {
      resource.listings(symbols, current, pageSize).onStart {
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