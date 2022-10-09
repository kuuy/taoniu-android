package com.kuuy.taoniu.data.cryptos.dto

import com.google.gson.annotations.SerializedName

data class StrategyListingsDto(
  @field:SerializedName("success")
  var success: Boolean,
  @field:SerializedName("data")
  val data: List<StrategyInfoDto>,
  @field:SerializedName("total")
  var total: Int,
  @field:SerializedName("current")
  var current: Int,
  @field:SerializedName("pageSize")
  var pageSize: Int,
)

