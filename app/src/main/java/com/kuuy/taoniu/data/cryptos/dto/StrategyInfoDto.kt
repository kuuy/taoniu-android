package com.kuuy.taoniu.data.cryptos.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StrategyInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("symbol")
  val symbol: String,
  @field:SerializedName("indicator")
  val indicator: String,
  @field:SerializedName("price")
  val price: Float,
  @field:SerializedName("signal")
  val signal: Int,
) : Parcelable
