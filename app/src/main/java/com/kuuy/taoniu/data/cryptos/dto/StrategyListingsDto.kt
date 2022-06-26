package com.kuuy.taoniu.data.cryptos.dto

import com.google.gson.annotations.SerializedName

data class StrategyListingsDto(
  @field:SerializedName("strategies")
  val strategies: List<StrategyInfoDto>,
)

