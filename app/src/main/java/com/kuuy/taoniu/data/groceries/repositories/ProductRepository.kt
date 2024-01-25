package com.kuuy.taoniu.data.groceries.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

import com.kuuy.taoniu.data.groceries.resources.ProductsResource
import com.kuuy.taoniu.data.groceries.dao.CounterOrderDao
import com.kuuy.taoniu.data.groceries.dto.ProductInfoDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.DbResult
import com.kuuy.taoniu.data.DtoPaginate

class ProductRepository @Inject constructor(
  private val productsResource: ProductsResource,
  private val counterOrderDao: CounterOrderDao
) {
  suspend fun listings(
    current: Int,
    pageSize: Int,
  ): Flow<ApiResource<DtoPaginate<ProductInfoDto>>> {
    return flow {
      productsResource.listings(current, pageSize).onStart {
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

  suspend fun get(id: String)
      : Flow<ApiResource<ProductDetailDto>> {
    return flow {
      productsResource.get(id).onStart {
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

  suspend fun create(
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResource<Nothing?>> {
    return flow {
      productsResource.create(
        title,
        intro,
        price,
        cover,
      ).onStart {
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

  suspend fun update(
    id: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResource<Nothing?>> {
    return flow {
      productsResource.update(
        id,
        title,
        intro,
        price,
        cover,
      ).onStart {
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

  fun addToCounter(
    id: String,
    title: String,
    price: Float
  ) : Flow<DbResource<Nothing?>> {
    return flow {
      counterOrderDao.addProduct(
        id,
        title,
        price
      ).onStart {
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

