package com.kuuy.taoniu.data.groceries.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class RelatedItemDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("resource_id")
  val resourceId: String,
) : Parcelable