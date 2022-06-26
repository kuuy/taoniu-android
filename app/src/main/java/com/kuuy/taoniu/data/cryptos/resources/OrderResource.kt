package com.kuuy.taoniu.data.cryptos.resources

import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.kuuy.taoniu.data.cryptos.api.OrderApi
import com.kuuy.taoniu.data.cryptos.dto.OrderListingsDto
import com.kuuy.taoniu.data.ApiResponse

class OrderResource @Inject constructor(
  private var orderApi: OrderApi 
) {
  suspend fun getOrderListings(): Flow<ApiResponse<OrderListingsDto>> {
    return flow {
      val response = orderApi.getOrderListings()
      emit(ApiResponse.Success(response))
    }.flowOn(Dispatchers.IO)
  }
}
