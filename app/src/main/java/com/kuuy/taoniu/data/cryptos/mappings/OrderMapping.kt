package com.kuuy.taoniu.data.cryptos.mappings

import com.kuuy.taoniu.data.cryptos.dto.OrderListingsDto
import com.kuuy.taoniu.data.cryptos.dto.OrderInfoDto
import com.kuuy.taoniu.data.cryptos.models.OrderListings
import com.kuuy.taoniu.data.cryptos.models.OrderInfo


fun OrderListingsDto.transform(): OrderListings {
  return OrderListings(
    orders=orders.transform(),
  )
}

fun List<OrderInfoDto>.transform(): List<OrderInfo> {
  return this.map { it.transform() }
}

fun OrderInfoDto.transform(): OrderInfo {
  return OrderInfo(
    remoteId=id,
    symbol,
    type,
    positionSide,
    side,
    price,
    status,
  )
}
