package com.kuuy.taoniu.data.cryptos.api

import retrofit2.http.GET;

import com.kuuy.taoniu.data.cryptos.dto.OrderListingsDto

interface OrderApi {
  @GET("orders")
  suspend fun getOrderListings(): OrderListingsDto 
}
