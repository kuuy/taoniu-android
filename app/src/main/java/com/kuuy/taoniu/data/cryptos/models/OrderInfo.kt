package com.kuuy.taoniu.data.cryptos.models

data class OrderInfo(
  val remoteId : String,
  val symbol : String,
  val type : String,
  val positionSide : String,
  val side : String,
  val price : Float,
  val status : String,
)
