package com.kuuy.taoniu.data.cryptos.api.binance.spot.analysis.tradings.fishers

import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.tradings.fishers.GridInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GridsApi {
  @GET("v1/binance/spot/analysis/tradings/fishers/grids")
  suspend fun listings(
    @Query("current") current: Int,
    @Query("page_size") pageSize: Int
  ): Response<DtoPaginate<GridInfoDto>>

  @GET("v1/binance/spot/analysis/tradings/fishers/grids/series")
  suspend fun series(
    @Query("limit") limit: Int,
  ): Response<DtoResponse<List<ArrayList<Any>>>>
}