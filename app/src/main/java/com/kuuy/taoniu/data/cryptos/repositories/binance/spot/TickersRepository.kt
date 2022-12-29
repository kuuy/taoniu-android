package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.TickersResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TickersRepository @Inject constructor(
  private val resource: TickersResource
) {
  suspend fun gets(
    symbols: List<String>,
    fields: List<String>,
  ) : Flow<ApiResource<DtoResponse<List<String>>>> {
    return flow {
      emit(ApiResource.Loading())
      try {
        when (val response = resource.gets(symbols, fields).first()) {
          is ApiResponse.Success -> {
            emit(ApiResource.Success(response.data))
          }
          is ApiResponse.Empty -> {
            emit(ApiResource.Success(null))
          }
          is ApiResponse.Error -> {
            emit(ApiResource.Error(response.errorMessage))
          }
        }
      } catch(e: Exception) {}
    }
  }
}