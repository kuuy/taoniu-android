package com.kuuy.taoniu.data.groceries.models

data class CounterBill(
  val totalAmount: Float,
  val totalDiscountAmount: Float,
  val totalPaymentAmount: Float,
  val totalUnit: Float,
  val totalNum: Float,
  val discount: Float,
  val discountAmount: Float,
  val reduceAmount: Float,
  val roundDownAmount: Float,
  val orders: List<CounterOrderInfo>,
)

