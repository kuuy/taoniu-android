package com.kuuy.taoniu.data.groceries.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedInfoDto(
  @field:SerializedName("id")
  val id: String,
  @field:SerializedName("type")
  val type: Int,
  @field:SerializedName("title")
  val title: String,
  @field:SerializedName("related_data")
  val relatedData: List<String>,
  @field:SerializedName("related_items")
  val relatedItems: List<RelatedItemDto>,
) : Parcelable