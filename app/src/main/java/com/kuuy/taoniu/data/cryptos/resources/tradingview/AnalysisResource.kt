package com.kuuy.taoniu.data.cryptos.resources.tradingview

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.api.tradingview.AnalysisApi
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisSummaryDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AnalysisResource @Inject constructor(
  private var analysisApi: AnalysisApi,
  private var gson: Gson,
) {
  suspend fun summary(
    exchange: String,
    symbol: String,
    interval: String,
  ): Flow<ApiResponse<AnalysisSummaryDto>> {
    return flow {
      val response = analysisApi.summary(exchange, symbol, interval)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }

  suspend fun listings(
    exchange: String,
    interval: String,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResponse<DtoPaginate<AnalysisInfoDto>>> {
    return flow {
      val response = analysisApi.listings(exchange, interval, current, pageSize)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }

  suspend fun gets(
    exchange: String,
    symbols: String,
    interval: String,
  ) : Flow<ApiResponse<List<AnalysisInfoDto>>> {
    return flow {
      val response = analysisApi.gets(exchange, symbols, interval)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }
}