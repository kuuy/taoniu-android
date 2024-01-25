package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.SymbolInfoDto
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.SymbolsResource

class SymbolsRepository @Inject constructor(
  private val resource: SymbolsResource
) {
  suspend fun get(
    symbol: String,
  ) : Flow<ApiResource<SymbolInfoDto>> {
    return flow {
      resource.get(symbol).onStart {
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

  suspend fun gets(
    symbols: List<String>,
  ) : Flow<ApiResource<List<String>>> {
    return flow {
      resource.gets(symbols).onStart {
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