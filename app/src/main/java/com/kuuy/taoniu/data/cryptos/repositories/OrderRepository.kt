package com.kuuy.taoniu.data.cryptos.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

import com.kuuy.taoniu.data.cryptos.resources.OrderResource
import com.kuuy.taoniu.data.cryptos.dto.OrderListingsDto
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse

class OrderRepository @Inject constructor(
  private val orderResource: OrderResource
) {
  suspend fun getOrderListings()
      : Flow<ApiResource<OrderListingsDto>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = orderResource.getOrderListings().first()) {
        is ApiResponse.Success -> {
          val data = response.data
          emit(ApiResource.Success(data))
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

