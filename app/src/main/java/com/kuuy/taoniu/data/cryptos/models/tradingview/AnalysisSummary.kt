package com.kuuy.taoniu.data.cryptos.models.tradingview

data class AnalysisSummary(
  val buy : Int,
  val neutral : Int,
  val sell : Int,
  var recommendation : String,
)