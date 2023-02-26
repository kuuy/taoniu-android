package com.kuuy.taoniu.data.cryptos.repositories.tradingview

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisSummaryDto
import com.kuuy.taoniu.data.cryptos.resources.tradingview.AnalysisResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnalysisRepository @Inject constructor(
  private val resource: AnalysisResource
) {
  suspend fun summary(
    exchange: String,
    symbol: String,
    interval: String,
  ) : Flow<ApiResource<DtoResponse<AnalysisSummaryDto>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.summary(exchange,symbol, interval).first()) {
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

  suspend fun listings(
    exchange: String,
    interval: String,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResource<DtoPaginate<AnalysisInfoDto>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.listings(exchange, interval, current, pageSize).first()) {
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
    exchange: String,
    symbols: List<String>,
    interval: String,
  ) : Flow<ApiResource<DtoResponse<List<AnalysisInfoDto>>>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = resource.gets(exchange, symbols.joinToString(","), interval).first()) {
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