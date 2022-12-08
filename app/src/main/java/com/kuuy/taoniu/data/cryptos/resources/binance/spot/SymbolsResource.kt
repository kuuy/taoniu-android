package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.SymbolsApi
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.SymbolInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SymbolsResource @Inject constructor(
  private var symbolsApi: SymbolsApi
) {
  suspend fun get(
    symbol: String,
  ) : Flow<ApiResponse<DtoResponse<SymbolInfoDto>>> {
    return flow {
      val response = symbolsApi.get(symbol)
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }

  suspend fun gets(
    symbols: List<String>,
  ) : Flow<ApiResponse<DtoResponse<List<String>>>> {
    return flow {
      val response = symbolsApi.gets(symbols.joinToString(","))
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}