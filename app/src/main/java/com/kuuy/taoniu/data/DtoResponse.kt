package com.kuuy.taoniu.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DtoResponse<T>(
  @field:SerializedName("success")
  var success: Boolean,
  @field:SerializedName("data")
  val data: @RawValue T
) : Parcelable