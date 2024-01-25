package com.kuuy.taoniu.data.groceries.api

import com.kuuy.taoniu.data.DtoResponse
import retrofit2.http.*;

import com.kuuy.taoniu.data.groceries.dto.ProductListingsDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import retrofit2.Response

interface StoresApi {
  @GET("v1/stores")
  suspend fun listings(): ProductListingsDto

  @GET("v1/stores/{id}")
  suspend fun get(
    @Path("id") id: String,
  ): Response<DtoResponse<ProductDetailDto>>

  @FormUrlEncoded
  @POST("v1/stores")
  suspend fun create(
    @Field("name") name: String,
    @Field("logo") logo: String,
  )
  @FormUrlEncoded
  @PUT("v1/stores/{id}")
  suspend fun update(
    @Path("id") id: String,
    @Field("name") name: String,
    @Field("logo") logo: String,
  )
}
