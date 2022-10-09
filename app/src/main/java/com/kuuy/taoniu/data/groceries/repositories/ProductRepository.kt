package com.kuuy.taoniu.data.groceries.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

import com.kuuy.taoniu.data.groceries.resources.ProductResource
import com.kuuy.taoniu.data.groceries.dao.CounterOrderDao
import com.kuuy.taoniu.data.groceries.dto.ProductListingsDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.dto.ProductBarcodeDto
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.DbResult

class ProductRepository @Inject constructor(
  private val productResource: ProductResource,
  private val counterOrderDao: CounterOrderDao
) {
  suspend fun getProductListings()
      : Flow<ApiResource<ProductListingsDto>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = productResource.getProductListings().first()) {
        is ApiResponse.Success -> {
          val data = response.data
          emit(ApiResource.Success(data))
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

  suspend fun getProductDetail(id: String)
      : Flow<ApiResource<ProductDetailDto>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = productResource.getProductDetail(
        id
      ).first()) {
        is ApiResponse.Success -> {
          val data = response.data
          emit(ApiResource.Success(data))
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

  suspend fun getProductBarcode(barcode: String)
      : Flow<ApiResource<ProductBarcodeDto>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = productResource.getProductBarcode(
        barcode
      ).first()) {
        is ApiResponse.Success -> {
          val data = response.data
          emit(ApiResource.Success(data))
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

  suspend fun createProduct(
    barcode: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResource<Nothing?>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = productResource.createProduct(
        barcode,
        title,
        intro,
        price,
        cover,
      ).first()) {
        is ApiResponse.Success -> {
          emit(ApiResource.Success(null))
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

  suspend fun updateProduct(
    id: String,
    barcode: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResource<Nothing?>> {
    return flow {
      emit(ApiResource.Loading())
      when (val response = productResource.updateProduct(
        id,
        barcode,
        title,
        intro,
        price,
        cover,
      ).first()) {
        is ApiResponse.Success -> {
          emit(ApiResource.Success(null))
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

  fun addToCounter(
    id: String,
    title: String,
    price: Float
  ) : Flow<DbResource<Nothing?>> {
    return flow {
      emit(DbResource.Loading())
      when (val result = counterOrderDao.addProduct(
        id,
        title,
        price
      ).first()) {
        is DbResult.Success -> {
          emit(DbResource.Success(null))
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

