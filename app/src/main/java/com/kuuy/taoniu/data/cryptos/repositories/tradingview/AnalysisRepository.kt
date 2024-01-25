package com.kuuy.taoniu.data.cryptos.repositories.tradingview

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisSummaryDto
import com.kuuy.taoniu.data.cryptos.resources.tradingview.AnalysisResource

class AnalysisRepository @Inject constructor(
  private val resource: AnalysisResource
) {
  suspend fun summary(
    exchange: String,
    symbol: String,
    interval: String,
  ) : Flow<ApiResource<AnalysisSummaryDto>> {
    return flow {
      resource.summary(exchange,symbol, interval).onStart {
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

  suspend fun listings(
    exchange: String,
    interval: String,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResource<DtoPaginate<AnalysisInfoDto>>> {
    return flow {
      resource.listings(exchange, interval, current, pageSize).onStart {
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
    exchange: String,
    symbols: List<String>,
    interval: String,
  ) : Flow<ApiResource<List<AnalysisInfoDto>>> {
    return flow {
      resource.gets(exchange, symbols.joinToString(","), interval).onStart {
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