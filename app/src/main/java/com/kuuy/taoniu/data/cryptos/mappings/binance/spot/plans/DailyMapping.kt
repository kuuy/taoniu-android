package com.kuuy.taoniu.data.cryptos.mappings.binance.spot.plans

import com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans.DailyInfoDto
import com.kuuy.taoniu.data.cryptos.models.binance.spot.plans.DailyInfo

fun List<DailyInfoDto>.transform(): List<DailyInfo> {
  return this.map { it.transform() }
}

fun DailyInfoDto.transform(): DailyInfo {
  return DailyInfo(
    remoteId=id,
    symbol,
    side,
    price,
    amount,
    status,
    timestamp,
    timestampFormat,
  )
}