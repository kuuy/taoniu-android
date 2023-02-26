package com.kuuy.taoniu.data.cryptos.resources.binance.spot.margin.isolated

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.SymbolsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SymbolsResource @Inject constructor(
  private var symbolsApi: SymbolsApi
) {
  suspend fun scan() : Flow<ApiResponse<DtoResponse<List<String>>>> {
    return flow {
      val response = symbolsApi.scan()
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}