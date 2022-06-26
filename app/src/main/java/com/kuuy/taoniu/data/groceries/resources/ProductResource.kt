package com.kuuy.taoniu.data.groceries.resources

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
      emit(ApiResponse.Success(response))
    }.flowOn(Dispatchers.IO)
  }

  suspend fun getProductBarcode(barcode: String)
      : Flow<ApiResponse<ProductBarcodeDto>> {
    return flow {
      var response = productApi.getProductBarcode(barcode)
      emit(ApiResponse.Success(response))
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
