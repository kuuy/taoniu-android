package com.kuuy.taoniu.data.groceries.api

import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import retrofit2.http.*;

import com.kuuy.taoniu.data.groceries.dto.ProductInfoDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import retrofit2.Response

interface ProductsApi {
  @GET("v1/products")
  suspend fun listings(
    @Query("current") current: Int,
    @Query("page_size") pageSize: Int
  ): Response<DtoPaginate<ProductInfoDto>>

  @GET("v1/products/{id}")
  suspend fun get(
    @Path("id") id: String,
  ): Response<DtoResponse<ProductDetailDto>>

  @FormUrlEncoded
  @POST("v1/products")
  suspend fun create(
    @Field("title") title: String,
    @Field("intro") intro: String,
    @Field("price") price: Float,
    @Field("cover") cover: String,
  )

  @FormUrlEncoded
  @PUT("v1/products/{id}")
  suspend fun update(
    @Path("id") id: String,
    @Field("title") title: String,
    @Field("intro") intro: String,
    @Field("price") price: Float,
    @Field("cover") cover: String,
  )
}
