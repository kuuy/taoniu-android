package com.kuuy.taoniu.data.images.api

import retrofit2.http.*
import okhttp3.MultipartBody

import com.kuuy.taoniu.data.images.dto.ImageInfoDto

interface ImageApi {
  @Multipart
  @POST("upload")
  suspend fun upload(
    @Part file: MultipartBody.Part
  ): ImageInfoDto 
}
