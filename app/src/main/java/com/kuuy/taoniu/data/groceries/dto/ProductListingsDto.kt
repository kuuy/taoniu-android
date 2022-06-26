package com.kuuy.taoniu.data.groceries.dto

import com.google.gson.annotations.SerializedName

data class ProductListingsDto(
  @field:SerializedName("products")
  val products: List<ProductInfoDto>,
)

