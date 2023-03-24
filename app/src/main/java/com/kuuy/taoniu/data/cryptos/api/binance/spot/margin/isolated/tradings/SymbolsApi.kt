package com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.tradings

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.http.GET

interface SymbolsApi {
  @GET("v1/binance/spot/margin/isolated/tradings/symbols/scan")
  suspend fun scan(): DtoResponse<List<String>>
}