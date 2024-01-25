package com.kuuy.taoniu.data.groceries.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoreInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("name")
  val name: String,
  @field:SerializedName("logo")
  val logo: String,
) : Parcelable