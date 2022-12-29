package com.kuuy.taoniu.data.cryptos.api.tradingview

import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisSummaryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnalysisApi {
  @GET("v1/tradingview/summary/{exchange},{symbol},{interval}")
  suspend fun summary(
    @Path("exchange") exchange: String,
    @Path("symbol") symbol: String,
    @Path("interval") interval: String,
  ): DtoResponse<AnalysisSummaryDto>

  @GET("v1/tradingview/analysis")
  suspend fun listings(
    @Query("exchange") exchange: String,
    @Query("interval") interval: String,
    @Query("current") current: Int,
    @Query("page_size") pageSize: Int
  ): DtoPaginate<AnalysisInfoDto>
}