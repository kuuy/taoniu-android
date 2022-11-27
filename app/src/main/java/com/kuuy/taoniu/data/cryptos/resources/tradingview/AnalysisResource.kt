package com.kuuy.taoniu.data.cryptos.resources.tradingview

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.api.tradingview.AnalysisApi
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnalysisResource @Inject constructor(
  private var analysisApi: AnalysisApi
) {
  suspend fun listings(
    exchange: String,
    interval: String,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResponse<DtoPaginate<AnalysisInfoDto>>> {
    return flow {
      val response = analysisApi.listings(exchange, interval, current, pageSize)
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}