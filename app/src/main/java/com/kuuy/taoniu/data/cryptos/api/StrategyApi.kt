package com.kuuy.taoniu.data.cryptos.api

import retrofit2.http.GET;

import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto

interface StrategyApi {
  @GET("strategies")
  suspend fun getStrategyListings(): StrategyListingsDto 
}
