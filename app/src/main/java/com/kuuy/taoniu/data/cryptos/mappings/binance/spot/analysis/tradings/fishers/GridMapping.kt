package com.kuuy.taoniu.data.cryptos.mappings.binance.spot.analysis.tradings.fishers

import com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.tradings.fishers.GridInfoDto
import com.kuuy.taoniu.data.cryptos.models.binance.spot.analysis.tradings.fishers.GridInfo

fun List<GridInfoDto>.transform(): List<GridInfo> {
  return this.map { it.transform() }
}

fun GridInfoDto.transform(): GridInfo {
  return GridInfo(
    remoteId=id,
    day,
    buysCount,
    sellsCount,
    buysAmount,
    sellsAmount,
  )
}