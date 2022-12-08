package com.kuuy.taoniu.data.cryptos.dto.binance.spot

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SymbolInfoDto(
  @field:SerializedName("symbol")
  val symbol: String,
  @field:SerializedName("base_asset")
  val baseAsset: String,
  @field:SerializedName("quote_asset")
  val quoteAsset: String,
) : Parcelable