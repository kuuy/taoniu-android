package com.kuuy.taoniu.data.account.repositories

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.account.dto.TokenDto
import com.kuuy.taoniu.data.account.resources.AuthResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
  private val authResource: AuthResource
) {
  suspend fun login(email: String, password: String)
      : Flow<ApiResource<TokenDto>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = authResource.login(email, password).firstOrNull()) {
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
