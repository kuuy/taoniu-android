package com.kuuy.taoniu.data.cryptos.dto.tradingview

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalysisSummaryDto(
  @field:SerializedName("BUY")
  val buy: Int,
  @field:SerializedName("NEUTRAL")
  val neutral: Int,
  @field:SerializedName("SELL")
  val sell: Int,
  @field:SerializedName("RECOMMENDATION")
  val recommendation: String,
) : Parcelable