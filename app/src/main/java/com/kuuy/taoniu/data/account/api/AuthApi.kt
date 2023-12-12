package com.kuuy.taoniu.data.account.api

import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.account.dto.TokenDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
  @FormUrlEncoded
  @POST("v1/login")
  suspend fun login(
    @Field("email") email: String,
    @Field("password") password: String
  ): Response<DtoResponse<TokenDto>>
}