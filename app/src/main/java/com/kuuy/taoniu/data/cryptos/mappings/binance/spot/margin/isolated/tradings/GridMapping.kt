package com.kuuy.taoniu.data.cryptos.mappings.binance.spot.margin.isolated.tradings

import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.isolated.tradings.GridInfoDto
import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.isolated.tradings.GridInfo

fun List<GridInfoDto>.transform(): List<GridInfo> {
  return this.map { it.transform() }
}

fun GridInfoDto.transform(): GridInfo {
  return GridInfo(
    remoteId=id,
    symbol,
    buyPrice,
    sellPrice,
    status,
    timestamp,
    timestampFormat,
  )
}