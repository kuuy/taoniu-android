package com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.margin.isolated.tradings.fishers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GridInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("day")
  val day: String,
  @field:SerializedName("buys_count")
  val buysCount: Int,
  @field:SerializedName("sells_count")
  val sellsCount: Int,
  @field:SerializedName("buys_amount")
  val buysAmount: Float,
  @field:SerializedName("sells_amount")
  var sellsAmount: Float,
) : Parcelable