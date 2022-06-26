package com.kuuy.taoniu.data.groceries.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("title")
  val title: String,
  @field:SerializedName("intro")
  val intro: String,
  @field:SerializedName("price")
  val price: Float,
  @field:SerializedName("cover")
  val cover: String,
) : Parcelable
