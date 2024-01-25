package com.kuuy.taoniu.data.cryptos.repositories.binance.spot

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

import com.kuuy.taoniu.data.*
import com.kuuy.taoniu.data.cryptos.dao.binance.spot.TickerDao
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.data.cryptos.resources.binance.spot.TickersResource

class TickersRepository @Inject constructor(
  private val resource: TickersResource,
  private val dao: TickerDao,
) {
  suspend fun gets(
    symbols: List<String>,
    fields: List<String>,
  ) : Flow<ApiResource<List<String>>> {
    return flow {
      resource.gets(symbols, fields).onStart {
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

  suspend fun save(symbol: String, ticker: Ticker) : Flow<DbResource<Nothing?>> {
    return flow {
      dao.save(symbol, ticker).onStart {
        emit(DbResource.Loading())
      }.catch {
        emit(DbResource.Success(null))
      }.collect { response ->
        when (response) {
          is DbResult.Success -> {
            val data = response.data
            emit(DbResource.Success(data))
          }
          is DbResult.Error -> {
            emit(DbResource.Error(response.errorMessage))
          }
          is DbResult.Empty -> {
            emit(DbResource.Success(null))
          }
        }
      }
    }
  }
}