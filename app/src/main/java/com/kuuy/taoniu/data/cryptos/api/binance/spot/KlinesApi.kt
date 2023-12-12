package com.kuuy.taoniu.data.cryptos.api.binance.spot

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KlinesApi {
  @GET("v1/binance/spot/klines")
  suspend fun series(
    @Query("symbol") symbol: String,
    @Query("interval") interval: String,
    @Query("limit") limit: Int,
  ): Response<DtoResponse<List<FloatArray>>>
}