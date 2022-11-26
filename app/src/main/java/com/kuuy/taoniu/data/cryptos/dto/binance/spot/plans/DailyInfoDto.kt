package com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("symbol")
  val symbol: String,
  @field:SerializedName("side")
  val side: Int,
  @field:SerializedName("price")
  val price: Float,
  @field:SerializedName("amount")
  var amount: Float,
  @field:SerializedName("status")
  var status: Int,
  @field:SerializedName("timestamp")
  var timestamp: Int,
  @field:SerializedName("timestamp_fmt")
  var timestampFormat: String,
) : Parcelable
