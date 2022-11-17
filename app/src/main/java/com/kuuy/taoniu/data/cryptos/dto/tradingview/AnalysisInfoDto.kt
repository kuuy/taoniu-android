package com.kuuy.taoniu.data.cryptos.dto.tradingview

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalysisInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("symbol")
  val symbol: String,
  @field:SerializedName("summary")
  val summary: AnalysisSummaryDto,
  @field:SerializedName("timestamp")
  var timestamp: Int,
) : Parcelable
