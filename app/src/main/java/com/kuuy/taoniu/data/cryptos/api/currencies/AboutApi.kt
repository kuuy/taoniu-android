package com.kuuy.taoniu.data.cryptos.api.currencies

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AboutApi {
  @GET("v1/currencies/about/{symbol}")
  suspend fun get(
    @Path("symbol") symbol: String,
  ): DtoResponse<String>
}