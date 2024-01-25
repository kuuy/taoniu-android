package com.kuuy.taoniu.data.groceries.api

import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.groceries.dto.FeedInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedsApi {
  @GET("v1/feeds")
  suspend fun listings(
    @Query("current") current: Int,
    @Query("page_size") pageSize: Int
  ): Response<DtoPaginate<FeedInfoDto>>
}