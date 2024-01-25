package com.kuuy.taoniu.data.cryptos.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import com.kuuy.taoniu.data.cryptos.resources.OrderResource
import com.kuuy.taoniu.data.cryptos.dto.OrderListingsDto
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

class OrderRepository @Inject constructor(
  private val orderResource: OrderResource
) {
  suspend fun getOrderListings()
      : Flow<ApiResource<OrderListingsDto>> {
    return flow {
      orderResource.getOrderListings().onStart {
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

