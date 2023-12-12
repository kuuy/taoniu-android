package com.kuuy.taoniu.data.groceries.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

import com.kuuy.taoniu.data.groceries.resources.ProductResource
import com.kuuy.taoniu.data.groceries.dao.CounterOrderDao
import com.kuuy.taoniu.data.groceries.models.CounterOrderListings
import com.kuuy.taoniu.data.groceries.models.CounterOrderDetail
import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.DbResult

class CounterRepository @Inject constructor(
  private val counterOrderDao: CounterOrderDao
) {
  suspend fun getOrderListings()
      : Flow<DbResource<CounterOrderListings>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.getOrderListings().firstOrNull()) {
        is DbResult.Success -> {
          val data = result.data
          emit(DbResource.Success(data))
        }
        is DbResult.Error -> {
          emit(DbResource.Error(result.errorMessage))
        }
        else -> {
          emit(DbResource.Success(null))
        }
      }
    }
  }

  suspend fun getOrderDetail(id: Long)
      : Flow<DbResource<CounterOrderDetail>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.getOrderDetail(
        id
      ).firstOrNull()) {
        is DbResult.Success -> {
          val data = result.data
          emit(DbResource.Success(data))
        }
        is DbResult.Error -> {
          emit(DbResource.Error(result.errorMessage))
        }
        else -> {
          emit(DbResource.Success(null))
        }
      }
    }
  }

  suspend fun removeOrder(id: Long)
      : Flow<DbResource<Nothing?>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.delete(
        id
      ).firstOrNull()) {
        is DbResult.Success -> {
          val data = result.data
          emit(DbResource.Success(data))
        }
        is DbResult.Error -> {
          emit(DbResource.Error(result.errorMessage))
        }
        else -> {
          emit(DbResource.Success(null))
        }
      }
    }
  }

  suspend fun clear()
      : Flow<DbResource<Nothing?>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.clear().firstOrNull()) {
        is DbResult.Success -> {
          val data = result.data
          emit(DbResource.Success(data))
        }
        is DbResult.Error -> {
          emit(DbResource.Error(result.errorMessage))
        }
        else -> {
          emit(DbResource.Success(null))
        }
      }
    }
  }

}

