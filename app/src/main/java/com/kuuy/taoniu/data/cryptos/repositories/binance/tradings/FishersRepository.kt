package com.kuuy.taoniu.data.cryptos.repositories.binance.tradings

import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.DbResult
import com.kuuy.taoniu.data.cryptos.dao.binance.tradings.FisherDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FishersRepository @Inject constructor(
  private val fisherDao: FisherDao,
) {
  suspend fun scan(scene: String): Flow<DbResource<Array<String>>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = fisherDao.scan(scene).first()) {
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

  suspend fun addAll(
    scene: String,
    symbols: Array<String>,
  ) : Flow<DbResource<Nothing?>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = fisherDao.addAll(scene, symbols).first()) {
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