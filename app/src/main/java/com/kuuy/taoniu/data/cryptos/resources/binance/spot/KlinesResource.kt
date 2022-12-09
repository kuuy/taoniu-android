package com.kuuy.taoniu.data.cryptos.resources.binance.spot

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.binance.spot.KlinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KlinesResource @Inject constructor(
  private var klinesApi: KlinesApi
) {
  suspend fun series(
    symbol: String,
    interval: String,
    limit: Int,
  ) : Flow<ApiResponse<DtoResponse<List<FloatArray>>>> {
    return flow {
      val response = klinesApi.series(symbol, interval, limit)
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}