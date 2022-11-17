package com.kuuy.taoniu.data.account.api

import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.account.dto.TokenDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenApi {
  @FormUrlEncoded
  @POST("v1/token/refresh")
  suspend fun refresh(@Field("refresh_token") refreshToken: String): DtoResponse<TokenDto>
}