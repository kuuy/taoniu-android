package com.kuuy.taoniu.data.cryptos.mappings.binance.spot

import com.kuuy.taoniu.data.cryptos.dto.binance.spot.SymbolInfoDto
import com.kuuy.taoniu.data.cryptos.models.binance.spot.SymbolInfo

fun List<SymbolInfoDto>.transform(): List<SymbolInfo> {
  return this.map { it.transform() }
}

fun SymbolInfoDto.transform(): SymbolInfo {
  return SymbolInfo(
    symbol,
    baseAsset,
    quoteAsset,
  )
}