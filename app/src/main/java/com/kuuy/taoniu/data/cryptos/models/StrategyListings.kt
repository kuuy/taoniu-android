package com.kuuy.taoniu.data.cryptos.models

data class StrategyListings(
  var success: Boolean,
  val data: List<StrategyInfo>,
  var total: Int,
  var current: Int,
  var pageSize: Int,
)

