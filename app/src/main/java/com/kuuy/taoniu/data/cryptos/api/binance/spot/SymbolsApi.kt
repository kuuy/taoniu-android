package com.kuuy.taoniu.data.cryptos.api.binance.spot

import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.SymbolInfoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SymbolsApi {
  @GET("v1/binance/spot/symbols/{symbol}")
  suspend fun get(
    @Path("symbol") symbol: String,
  ): DtoResponse<SymbolInfoDto>

  @GET("v1/binance/spot/symbols")
  suspend fun gets(
    @Query("symbols") symbols: String,
  ): DtoResponse<List<String>>
}