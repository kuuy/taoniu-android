package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.SymbolInfoDto
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.SymbolsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SymbolsRepository @Inject constructor(
  private val resource: SymbolsResource
) {
  suspend fun get(
    symbol: String,
  ) : Flow<ApiResource<DtoResponse<SymbolInfoDto>>> {
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
          emit(ApiResource.Error(response.errorMessage))
        }
      }
    }
  }

  suspend fun gets(
    symbols: List<String>,
  ) : Flow<ApiResource<DtoResponse<List<String>>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.gets(symbols).first()) {
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
    }
  }
}