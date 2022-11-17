package com.kuuy.taoniu.data.cryptos.models.tradingview

data class AnalysisInfo(
  val remoteId : String,
  val symbol : String,
  var summary : AnalysisSummary,
  val timestamp : Int,
)