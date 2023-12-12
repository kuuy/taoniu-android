package com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.Response
import retrofit2.http.GET

interface SymbolsApi {
  @GET("v1/binance/spot/margin/isolated/symbols/scan")
  suspend fun scan(): Response<DtoResponse<List<String>>>
}
