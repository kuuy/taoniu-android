package com.kuuy.taoniu.data.groceries.dto
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BarcodeInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("barcode")
  val barcode: String,
  @field:SerializedName("product_id")
  val productId: String,
) : Parcelable