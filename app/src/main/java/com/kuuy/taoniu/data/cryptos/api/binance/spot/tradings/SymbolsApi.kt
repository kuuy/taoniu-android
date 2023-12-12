package com.kuuy.taoniu.data.cryptos.api.binance.spot.tradings

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.Response
import retrofit2.http.GET

interface SymbolsApi {
  @GET("v1/binance/spot/tradings/symbols/scan")
  suspend fun scan(): Response<DtoResponse<List<String>>>
}