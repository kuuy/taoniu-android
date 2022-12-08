package com.kuuy.taoniu.data.cryptos.resources.binance.spot.plans

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.api.binance.spot.plans.DailyApi
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans.DailyInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DailyResource @Inject constructor(
  private var dailyApi: DailyApi
) {
  suspend fun listings(
    symbols: List<String>,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResponse<DtoPaginate<DailyInfoDto>>> {
    return flow {
      val response = dailyApi.listings(symbols.joinToString(","), current, pageSize)
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}