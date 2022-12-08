package com.kuuy.taoniu.data.cryptos.models.binance.spot

data class SymbolInfo(
  val symbol : String,
  var baseAsset : String,
  var quoteAsset : String,
)