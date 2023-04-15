package com.kuuy.taoniu.data.cryptos.models

data class Ticker(
  var open : Float,
  var price : Float,
  var high: Float,
  var low: Float,
  var volume: Float,
  var quota: Float,
  var state : Int,
  var change: Float,
  var changeState: Int,
)