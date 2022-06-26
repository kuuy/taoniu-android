package com.kuuy.taoniu.data.groceries.models

data class ProductDetail(
  val remoteId : String,
  val barcode : String,
  val title : String,
  val intro : String,
  val price : Float,
  val cover : String,
)
