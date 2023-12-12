package com.kuuy.taoniu.data.cryptos.api


import retrofit2.http.GET;
import retrofit2.Response

import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.OrderListingsDto

interface OrderApi {
  @GET("orders")
  suspend fun getOrderListings(): Response<DtoResponse<OrderListingsDto>>
}
