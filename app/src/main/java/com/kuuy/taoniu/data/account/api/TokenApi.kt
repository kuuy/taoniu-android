package com.kuuy.taoniu.data.account.api

import com.kuuy.taoniu.data.account.dto.TokenDto
import retrofit2.http.POST

interface TokenApi {
  @POST("v1/token/refresh")
  suspend fun refresh(): TokenDto
}