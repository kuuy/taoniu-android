package com.kuuy.taoniu.data.account.resources

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.account.api.AuthApi
import com.kuuy.taoniu.data.account.dto.TokenDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.EOFException
import javax.inject.Inject

class AuthResource @Inject constructor(
  private var authApi: AuthApi
) {
  suspend fun login(email: String, password: String): Flow<ApiResponse<TokenDto>> {
    return flow {
      val response = authApi.login(email, password)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        try {
          response.errorBody()?.let {
            var apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
            emit(ApiResponse.Error(apiError))
          }
        } catch (e: Throwable) {}
      }
    }.flowOn(Dispatchers.IO)
  }
}