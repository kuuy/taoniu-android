package com.kuuy.taoniu.data.cryptos.api.binance.spot.margin

import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.OrderInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersApi {
  @GET("v1/binance/spot/margin/orders")
  suspend fun listings(
    @Query("symbols") symbols: String,
    @Query("current") current: Int,
    @Query("page_size") pageSize: Int
  ): Response<DtoPaginate<OrderInfoDto>>
}