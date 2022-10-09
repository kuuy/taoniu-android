package com.kuuy.taoniu.data.cryptos.models

data class StrategyInfo(
  val remoteId : String,
  val symbol : String,
  val indicator : String,
  val price : Float,
  val signal : Int,
  var createdAt : String,
)
