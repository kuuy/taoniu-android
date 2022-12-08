package com.kuuy.taoniu.data.cryptos.models.binance.spot.plans

data class DailyInfo(
  val remoteId : String,
  val symbol : String,
  val side : Int,
  var price : Float,
  var amount : Float,
  var status : Int,
  val timestamp : Long,
  val timestampFormat : String,
)