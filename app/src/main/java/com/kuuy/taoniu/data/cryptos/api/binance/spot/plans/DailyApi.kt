package com.kuuy.taoniu.data.cryptos.api.binance.spot.plans

import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans.DailyInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyApi {
  @GET("v1/binance/spot/plans/daily")
  suspend fun listings(
    @Query("symbols") symbols: String,
    @Query("current") current: Int,
    @Query("page_size") pageSize: Int
  ): Response<DtoPaginate<DailyInfoDto>>
}