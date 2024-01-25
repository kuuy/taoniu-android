package com.kuuy.taoniu.data.account.resources

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.account.api.AuthApi
import com.kuuy.taoniu.data.account.dto.TokenDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthResource @Inject constructor(
  private var authApi: AuthApi,
  private var gson: Gson,
) {
  suspend fun login(email: String, password: String): Flow<ApiResponse<TokenDto>> {
    return flow {
      val response = authApi.login(email, password)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.flowOn(Dispatchers.IO)
  }
}