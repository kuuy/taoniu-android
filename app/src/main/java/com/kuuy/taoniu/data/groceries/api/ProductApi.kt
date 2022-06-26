package com.kuuy.taoniu.data.groceries.api

import retrofit2.http.*;

import com.kuuy.taoniu.data.groceries.dto.ProductListingsDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.dto.ProductBarcodeDto

interface ProductApi {
  @GET("products")
  suspend fun getProductListings(): ProductListingsDto 

  @GET("products/{id}")
  suspend fun getProductDetail(
    @Path("id") id: String,
  ): ProductDetailDto


  @GET("products/barcode/{barcode}")
  suspend fun getProductBarcode(
    @Path("barcode") barcode: String,
  ): ProductBarcodeDto

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
