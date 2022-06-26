package com.kuuy.taoniu.data.groceries.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductBarcodeDto(
  @field:SerializedName("barcode")
  val barcode: String,
  @field:SerializedName("product_id")
  val productId: String,
) : Parcelable
