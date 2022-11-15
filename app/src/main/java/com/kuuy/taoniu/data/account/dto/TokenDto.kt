package com.kuuy.taoniu.data.account.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TokenDto(
  @field:SerializedName("access_token")
  val access: String,
  @field:SerializedName("refresh_token")
  val refresh: String,
) : Parcelable
