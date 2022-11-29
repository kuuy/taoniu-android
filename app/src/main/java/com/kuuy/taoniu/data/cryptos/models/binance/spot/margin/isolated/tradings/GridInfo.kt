package com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.isolated.tradings

data class GridInfo(
  val remoteId : String,
  val symbol : String,
  var buyPrice : Float,
  var sellPrice : Float,
  var status : Int,
  val timestamp : Int,
  val timestampFormat : String,
)