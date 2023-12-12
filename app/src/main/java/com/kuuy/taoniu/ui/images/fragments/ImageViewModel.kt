package com.kuuy.taoniu.ui.images.fragments

import android.net.Uri
import android.content.ContentResolver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.images.dto.ImageInfoDto
import com.kuuy.taoniu.data.images.repositories.ImageRepository
import com.kuuy.taoniu.ui.base.BaseViewModel

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: ImageRepository
) : BaseViewModel() {

  private val _imageInfo
      = MutableLiveData<ApiResource<ImageInfoDto>>()
  val imageInfo: LiveData<ApiResource<ImageInfoDto>> get()
      = _imageInfo

  fun upload(uri: Uri, contentResolver: ContentResolver) {
    viewModelScope.launch {
      repository.upload(uri, contentResolver)
          .onStart {
            _imageInfo.postValue(ApiResource.Loading())
          }.catch {
            it.message?.let { message ->
              _imageInfo.postValue(ApiResource.Error(ApiError(500, message)))
            }
          }.collect { response ->
            response.data.let {
              _imageInfo.postValue(ApiResource.Success(it))
            }
          }
    }
  }
}
