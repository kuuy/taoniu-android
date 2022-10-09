package com.kuuy.taoniu.data.groceries.models

data class CounterOrderDetail(
  val id : Long,
  val productId : String,
  val title : String,
  val price : Float,
  val discount : Float,
  val discountPrice : Float,
  val reducePrice : Float,
  val quantity : Int,
)
