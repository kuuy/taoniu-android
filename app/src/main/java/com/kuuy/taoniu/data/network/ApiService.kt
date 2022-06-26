package com.kuuy.taoniu.data.network

import com.kuuy.taoniu.data.model.NewsResponse
import com.kuuy.taoniu.utils.Constants.API_KEY
import com.kuuy.taoniu.utils.Constants.QUERY_SOURCES
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("sources") sources: String = QUERY_SOURCES,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

}