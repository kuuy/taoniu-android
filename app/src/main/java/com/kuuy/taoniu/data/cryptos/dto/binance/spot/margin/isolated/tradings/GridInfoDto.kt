package com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.isolated.tradings

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GridInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("symbol")
  val symbol: String,
  @field:SerializedName("buy_price")
  val buyPrice: Float,
  @field:SerializedName("sell_price")
  val sellPrice: Float,
  @field:SerializedName("status")
  var status: Int,
  @field:SerializedName("timestamp")
  var timestamp: Long,
  @field:SerializedName("timestamp_fmt")
  var timestampFormat: String,
) : Parcelable