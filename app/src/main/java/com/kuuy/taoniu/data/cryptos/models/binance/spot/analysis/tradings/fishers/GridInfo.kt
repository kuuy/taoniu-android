package com.kuuy.taoniu.data.cryptos.models.binance.spot.analysis.tradings.fishers

data class GridInfo(
  val remoteId : String,
  val day : String,
  var buysCount : Int,
  var sellsCount : Int,
  var buysAmount : Float,
  val sellsAmount : Float,
)