package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.TradingsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TradingsResource @Inject constructor(
  private var tradingsApi: TradingsApi
) {
  suspend fun scan() : Flow<ApiResponse<DtoResponse<List<String>>>> {
    return flow {
      val response = tradingsApi.scan()
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}