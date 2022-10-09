package com.kuuy.taoniu.data.groceries.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
      when (val result = counterOrderDao.getOrderListings().first()) {
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

  suspend fun getOrderDetail(id: Long)
      : Flow<DbResource<CounterOrderDetail>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.getOrderDetail(
        id
      ).first()) {
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

  suspend fun removeOrder(id: Long)
      : Flow<DbResource<Nothing?>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.delete(
        id
      ).first()) {
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

  suspend fun clear()
      : Flow<DbResource<Nothing?>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.clear().first()) {
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

