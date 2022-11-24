package com.kuuy.taoniu.data.cryptos.models.tradingview

data class AnalysisInfo(
  val remoteId : String,
  val symbol : String,
  val price: Float?,
  var summary : AnalysisSummary,
  val timestamp : Int,
)