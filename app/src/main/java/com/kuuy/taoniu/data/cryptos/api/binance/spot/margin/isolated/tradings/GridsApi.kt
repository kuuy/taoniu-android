package com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.tradings

import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.isolated.tradings.GridInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GridsApi {
  @GET("v1/binance/spot/margin/isolated/tradings/grids")
  suspend fun listings(
    @Query("symbols") symbols: String,
    @Query("current") current: Int,
    @Query("page_size") pageSize: Int
  ): DtoPaginate<GridInfoDto>
}