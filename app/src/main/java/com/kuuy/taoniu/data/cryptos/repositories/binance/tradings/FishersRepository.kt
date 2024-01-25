package com.kuuy.taoniu.data.cryptos.repositories.binance.tradings

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.DbResult
import com.kuuy.taoniu.data.cryptos.dao.binance.tradings.FisherDao

class FishersRepository @Inject constructor(
  private val fisherDao: FisherDao,
) {
  suspend fun scan(scene: String): Flow<DbResource<Array<String>>> {
    return flow {
      fisherDao.scan(scene).onStart {
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

  suspend fun addAll(
    scene: String,
    symbols: Array<String>,
  ) : Flow<DbResource<Nothing?>> {
    return flow {
      fisherDao.addAll(scene, symbols).onStart {
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