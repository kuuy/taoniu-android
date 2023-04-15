package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import com.kuuy.taoniu.data.*
import com.kuuy.taoniu.data.cryptos.dao.binance.spot.TickerDao
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.TickersResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TickersRepository @Inject constructor(
  private val resource: TickersResource,
  private val dao: TickerDao,
) {
  suspend fun gets(
    symbols: List<String>,
    fields: List<String>,
  ) : Flow<ApiResource<DtoResponse<List<String>>>> {
    return flow {
      emit(ApiResource.Loading())
      try {
        when (val response = resource.gets(symbols, fields).first()) {
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
      } catch(e: Exception) {}
    }
  }

  suspend fun save(symbol: String, ticker: Ticker) : Flow<DbResource<Nothing?>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = dao.save(symbol, ticker).first()) {
        is DbResult.Success -> {
          val data = result.data
          emit(DbResource.Success(data))
        }
        is DbResult.Empty -> {
          emit(DbResource.Success(null))
        }
        is DbResult.Error -> {
          emit(DbResource.Error(result.errorMessage))
        }
      }
    }
  }
}