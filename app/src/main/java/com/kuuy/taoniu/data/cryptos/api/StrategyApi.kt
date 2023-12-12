package com.kuuy.taoniu.data.cryptos.api

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto
import retrofit2.Response

interface StrategyApi {
  @GET("v1/strategies")
  suspend fun getStrategyListings(
    @Query("current") current: Int,
    @Query("pageSize") pageSize: Int
  ): Response<DtoResponse<StrategyListingsDto>>
}
