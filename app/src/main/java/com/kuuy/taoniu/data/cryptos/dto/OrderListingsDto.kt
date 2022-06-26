package com.kuuy.taoniu.data.cryptos.dto

import com.google.gson.annotations.SerializedName

data class OrderListingsDto(
  @field:SerializedName("orders")
  val orders: List<OrderInfoDto>,
)

