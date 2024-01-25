package com.kuuy.taoniu.ui.account.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.account.dto.TokenDto
import com.kuuy.taoniu.data.account.repositories.AuthRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val repository: AuthRepository
) : BaseViewModel() {

  private val _token = MutableLiveData<ApiResource<TokenDto>>()
  val token: LiveData<ApiResource<TokenDto>>
    get() = _token

  fun login(email: String, password: String) {
    viewModelScope.launch {
      repository.login(email, password).catch {
        _token.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _token.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _token.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _token.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }
}
