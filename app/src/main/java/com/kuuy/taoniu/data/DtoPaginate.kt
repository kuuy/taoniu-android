package com.kuuy.taoniu.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DtoPaginate<T>(
  @field:SerializedName("success")
  var success: Boolean,
  @field:SerializedName("data")
  val data: @RawValue List<T>,
  @field:SerializedName("total")
  var total: Int,
  @field:SerializedName("current")
  var current: Int,
  @field:SerializedName("pageSize")
  var pageSize: Int,
) : Parcelable