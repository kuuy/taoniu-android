package com.kuuy.taoniu.data.account.resources

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.account.api.TokenApi
import com.kuuy.taoniu.data.account.dto.TokenDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TokenResource @Inject constructor(
  private var tokenApi: TokenApi
) {
  suspend fun refresh(refreshToken: String): Flow<ApiResponse<TokenDto>> {
    return flow {
      val response = tokenApi.refresh(refreshToken)
      emit(ApiResponse.Success(response.data))
    }.flowOn(Dispatchers.IO)
  }
}