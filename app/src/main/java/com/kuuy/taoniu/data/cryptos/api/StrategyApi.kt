package com.kuuy.taoniu.data.cryptos.api

import retrofit2.http.GET;
import retrofit2.http.Query;

import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto

interface StrategyApi {
  @GET("v1/strategies")
  suspend fun getStrategyListings(
    @Query("current") current: Int,
    @Query("pageSize") pageSize: Int
  ): StrategyListingsDto
}
