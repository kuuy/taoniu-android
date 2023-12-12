package com.kuuy.taoniu.data.groceries.api

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.http.*;

import com.kuuy.taoniu.data.groceries.dto.ProductListingsDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.dto.ProductBarcodeDto
import retrofit2.Response

interface ProductApi {
  @GET("products")
  suspend fun getProductListings(): ProductListingsDto 

  @GET("products/{id}")
  suspend fun getProductDetail(
    @Path("id") id: String,
  ): Response<DtoResponse<ProductDetailDto>>


  @GET("products/barcode/{barcode}")
  suspend fun getProductBarcode(
    @Path("barcode") barcode: String,
  ): Response<DtoResponse<ProductBarcodeDto>>

  @FormUrlEncoded
  @POST("products")
  suspend fun createProduct(
    @Field("barcode") barcode: String,
    @Field("title") title: String,
    @Field("intro") intro: String,
    @Field("price") price: Float,
    @Field("cover") cover: String,
  )

  @FormUrlEncoded
  @PUT("products/{id}")
  suspend fun updateProduct(
    @Path("id") id: String,
    @Field("barcode") barcode: String,
    @Field("title") title: String,
    @Field("intro") intro: String,
    @Field("price") price: Float,
    @Field("cover") cover: String,
  )

}
