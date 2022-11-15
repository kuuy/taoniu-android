package com.kuuy.taoniu.data.account.resources

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.account.api.AuthApi
import com.kuuy.taoniu.data.account.dto.TokenDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthResource @Inject constructor(
  private var authApi: AuthApi
) {
  suspend fun login(email: String, password: String): Flow<ApiResponse<TokenDto>> {
    return flow {
      val response = authApi.login(email, password)
      emit(ApiResponse.Success(response.data))
    }.flowOn(Dispatchers.IO)
  }
}