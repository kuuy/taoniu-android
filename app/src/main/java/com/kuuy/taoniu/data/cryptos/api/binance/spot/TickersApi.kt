package com.kuuy.taoniu.data.cryptos.api.binance.spot

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TickersApi {
  @GET("v1/binance/spot/tickers")
  suspend fun gets(
    @Query("symbols") symbols: String,
    @Query("fields") fields: String,
  ): DtoResponse<List<String>>
}