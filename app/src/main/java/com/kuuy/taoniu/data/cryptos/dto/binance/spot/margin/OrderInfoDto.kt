package com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("symbol")
  val symbol: String,
  @field:SerializedName("side")
  val side: String,
  @field:SerializedName("price")
  val price: Float,
  @field:SerializedName("quantity")
  val quantity: Float,
  @field:SerializedName("status")
  var status: String,
  @field:SerializedName("timestamp")
  var timestamp: Long,
  @field:SerializedName("timestamp_fmt")
  var timestampFormat: String,
) : Parcelable