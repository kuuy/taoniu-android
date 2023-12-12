package com.kuuy.taoniu.data.cryptos.repositories.currencies

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.resources.currencies.AboutResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AboutRepository @Inject constructor(
  private val resource: AboutResource
) {
  suspend fun get(
    symbol: String,
  ): Flow<ApiResource<String>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.get(symbol).first()) {
        is ApiResponse.Success -> {
          emit(ApiResource.Success(response.data))
        }
        is ApiResponse.Empty -> {
          emit(ApiResource.Success(null))
        }
        is ApiResponse.Error -> {
          emit(ApiResource.Error(response.apiError))
        }
      }
    }
  }
}