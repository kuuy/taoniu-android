package com.kuuy.taoniu.data.cryptos.mappings

import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto
import com.kuuy.taoniu.data.cryptos.dto.StrategyInfoDto
import com.kuuy.taoniu.data.cryptos.models.StrategyListings
import com.kuuy.taoniu.data.cryptos.models.StrategyInfo


fun StrategyListingsDto.transform(): StrategyListings {
  return StrategyListings(
    success=success,
    data=data.transform(),
    total=total,
    current=current,
    pageSize=pageSize,
  )
}

fun List<StrategyInfoDto>.transform(): List<StrategyInfo> {
  return this.map { it.transform() }
}

fun StrategyInfoDto.transform(): StrategyInfo {
  return StrategyInfo(
    remoteId=id,
    symbol,
    indicator,
    price,
    signal,
    createdAt,
  )
}
