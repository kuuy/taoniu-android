package com.kuuy.taoniu.data.images.resources

import javax.inject.Inject

import android.net.Uri
import android.content.ContentResolver

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

import com.kuuy.taoniu.data.images.api.ImageApi
import com.kuuy.taoniu.data.images.dto.ImageInfoDto
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.UriRequestBody

class ImageResource @Inject constructor(
  private var imageApi: ImageApi 
) {

  suspend fun upload(
    uri: Uri,
    contentResolver: ContentResolver
  ): Flow<ApiResponse<ImageInfoDto>> {
    return flow {
      val file = MultipartBody.Part.createFormData(
        "file",
        "file.me",
        UriRequestBody(uri, contentResolver)
      )
      var response = imageApi.upload(file)
      emit(ApiResponse.Success(response))
    }.flowOn(Dispatchers.IO)
  }

}
