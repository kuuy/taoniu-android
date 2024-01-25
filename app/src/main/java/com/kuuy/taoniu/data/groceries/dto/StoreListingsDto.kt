package com.kuuy.taoniu.data.groceries.dto

import com.google.gson.annotations.SerializedName

data class StoreListingsDto(
  @field:SerializedName("products")
  val products: List<StoreInfoDto>,
)