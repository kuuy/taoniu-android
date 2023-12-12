package com.kuuy.taoniu.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiError(
  @field:SerializedName("code")
  val code: Int = 0,
  @field:SerializedName("message")
  val message: String = "",
) : Parcelable