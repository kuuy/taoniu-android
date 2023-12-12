package com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.Response
import retrofit2.http.GET

interface TradingsApi {
  @GET("v1/binance/spot/margin/isolated/tradings/scan")
  suspend fun scan(): Response<DtoResponse<List<String>>>
}