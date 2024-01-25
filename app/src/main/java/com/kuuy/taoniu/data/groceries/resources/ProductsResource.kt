package com.kuuy.taoniu.data.groceries.resources

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.kuuy.taoniu.data.groceries.api.ProductsApi
import com.kuuy.taoniu.data.groceries.dto.ProductInfoDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import kotlinx.coroutines.flow.catch

class ProductsResource @Inject constructor(
  private var productsApi: ProductsApi,
  private var gson: Gson,
) {
  suspend fun listings(
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResponse<DtoPaginate<ProductInfoDto>>> {
    return flow {
      val response = productsApi.listings(current, pageSize)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }

  suspend fun get(id: String)
      : Flow<ApiResponse<ProductDetailDto>> {
    return flow {
      var response = productsApi.get(id)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.flowOn(Dispatchers.IO)
  }

  suspend fun create(
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResponse<Nothing?>> {
    return flow {
      productsApi.create(
        title,
        intro,
        price,
        cover,
      )
      emit(ApiResponse.Success(null))
    }
  }

  suspend fun update(
    id: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResponse<Nothing?>> {
    return flow {
      productsApi.update(
        id,
        title,
        intro,
        price,
        cover,
      )
      emit(ApiResponse.Success(null))
    }
  }
}
