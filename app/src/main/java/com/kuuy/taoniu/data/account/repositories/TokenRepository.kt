package com.kuuy.taoniu.data.account.repositories

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.account.dto.TokenDto
import com.kuuy.taoniu.data.account.resources.TokenResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class TokenRepository @Inject constructor(
  private val tokenResource: TokenResource
) {
  suspend fun refresh(refreshToken: String)
      : Flow<ApiResource<TokenDto>> {
    return flow {
      tokenResource.refresh(refreshToken).onStart {
        emit(ApiResource.Loading())
      }.catch {
        emit(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResponse.Success -> {
            val data = response.data
            emit(ApiResource.Success(data))
          }
          is ApiResponse.Error -> {
            emit(ApiResource.Error(response.apiError))
          }
          is ApiResponse.Empty -> {
            emit(ApiResource.Success(null))
          }
        }
      }
    }
  }
}