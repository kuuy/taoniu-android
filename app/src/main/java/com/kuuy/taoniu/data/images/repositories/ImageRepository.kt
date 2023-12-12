package com.kuuy.taoniu.data.images.repositories

import javax.inject.Inject

import android.net.Uri
import android.content.ContentResolver

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

import com.kuuy.taoniu.data.images.resources.ImageResource
import com.kuuy.taoniu.data.images.dto.ImageInfoDto
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse

class ImageRepository @Inject constructor(
  private val imageResource: ImageResource
) {

  suspend fun upload(
    uri: Uri,
    contentResolver: ContentResolver
  ) : Flow<ApiResource<ImageInfoDto>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response =
          imageResource.upload(uri, contentResolver).firstOrNull()) {
        is ApiResponse.Success -> {
          val data = response.data
          emit(ApiResource.Success(data))
        }
        is ApiResponse.Error -> {
          emit(ApiResource.Error(response.apiError))
        }
        else -> {
          emit(ApiResource.Success(null))
        }
      }
    }
  }

}

