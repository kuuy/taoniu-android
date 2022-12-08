package com.kuuy.taoniu.data.cryptos.mappings.binance.spot.margin

import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.OrderInfoDto
import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.OrderInfo

fun List<OrderInfoDto>.transform(): List<OrderInfo> {
  return this.map { it.transform() }
}

fun OrderInfoDto.transform(): OrderInfo {
  return OrderInfo(
    remoteId=id,
    symbol,
    side,
    price,
    quantity,
    status,
    timestamp,
    timestampFormat,
  )
}