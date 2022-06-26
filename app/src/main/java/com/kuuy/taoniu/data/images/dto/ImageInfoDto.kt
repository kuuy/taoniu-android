package com.kuuy.taoniu.data.images.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageInfoDto(
  @field:SerializedName("filename")
  val filename: String
) : Parcelable
