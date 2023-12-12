package com.kuuy.taoniu.data.groceries.resources

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.kuuy.taoniu.data.ApiError
import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.kuuy.taoniu.data.groceries.api.ProductApi
import com.kuuy.taoniu.data.groceries.dto.ProductListingsDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.dto.ProductBarcodeDto
import com.kuuy.taoniu.data.ApiResponse

class ProductResource @Inject constructor(
  private var productApi: ProductApi 
) {
  suspend fun getProductListings()
      : Flow<ApiResponse<ProductListingsDto>> {
    return flow {
      val response = productApi.getProductListings()
      emit(ApiResponse.Success(response))
    }.flowOn(Dispatchers.IO)
  }

  suspend fun getProductDetail(id: String)
      : Flow<ApiResponse<ProductDetailDto>> {
    return flow {
      var response = productApi.getProductDetail(id)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        var apiError = ApiError(
          response.code(),
          response.message(),
        )
        try {
          response.errorBody()?.let {
            apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
          }
        } catch (ioException: JsonIOException) {
        } catch (syntaxException: JsonSyntaxException) {
        }
        emit(ApiResponse.Error(apiError))
      }
    }.flowOn(Dispatchers.IO)
  }

  suspend fun getProductBarcode(barcode: String)
      : Flow<ApiResponse<ProductBarcodeDto>> {
    return flow {
      var response = productApi.getProductBarcode(barcode)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it.data))
        }
      } else {
        var apiError = ApiError(
          response.code(),
          response.message(),
        )
        try {
          response.errorBody()?.let {
            apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
          }
        } catch (ioException: JsonIOException) {
        } catch (syntaxException: JsonSyntaxException) {
        }
        emit(ApiResponse.Error(apiError))
      }
    }.flowOn(Dispatchers.IO)
  }

  suspend fun createProduct(
    barcode: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResponse<Nothing?>> {
    return flow {
      productApi.createProduct(
        barcode,
        title,
        intro,
        price,
        cover,
      )
      emit(ApiResponse.Success(null))
    }
  }

  suspend fun updateProduct(
    id: String,
    barcode: String,
    title: String,
    intro: String,
    price: Float,
    cover: String,
  ) : Flow<ApiResponse<Nothing?>> {
    return flow {
      productApi.updateProduct(
        id,
        barcode,
        title,
        intro,
        price,
        cover,
      )
      emit(ApiResponse.Success(null))
    }
  }
}
