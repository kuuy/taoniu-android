package com.kuuy.taoniu.data.cryptos.models.binance.spot.margin

data class OrderInfo(
  val remoteId : String,
  val symbol : String,
  var side : String,
  var price : Float,
  var quantity : Float,
  var status : String,
  val timestamp : Long,
  val timestampFormat : String,
)