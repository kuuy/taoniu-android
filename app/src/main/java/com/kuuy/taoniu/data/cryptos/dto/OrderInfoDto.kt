package com.kuuy.taoniu.data.cryptos.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("symbol")
  val symbol: String,
  @field:SerializedName("type")
  val type: String,
  @field:SerializedName("position_side")
  val positionSide: String,
  @field:SerializedName("side")
  val side: String,
  @field:SerializedName("price")
  val price: Float,
  @field:SerializedName("status")
  val status: String,
) : Parcelable
