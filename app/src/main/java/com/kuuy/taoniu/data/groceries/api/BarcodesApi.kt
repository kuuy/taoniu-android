package com.kuuy.taoniu.data.groceries.api

import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.groceries.dto.BarcodeInfoDto
import retrofit2.http.*;

import retrofit2.Response

interface BarcodesApi {
  @GET("v1/barcodes/{id}")
  suspend fun get(
    @Path("id") id: String,
  ): Response<DtoResponse<BarcodeInfoDto>>
  @FormUrlEncoded
  @POST("v1/barcodes")
  suspend fun create(
    @Field("barcode") barcode: String,
    @Field("product_id") productId: String,
  )
  @FormUrlEncoded
  @PUT("v1/barcodes/{id}")
  suspend fun update(
    @Path("id") id: String,
    @Field("barcode") barcode: String,
    @Field("product_id") productId: String,
  )
}
