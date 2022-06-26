package com.kuuy.taoniu.data.source.remote

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.model.NewsResponse
import com.kuuy.taoniu.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getTopNews(): Flow<ApiResponse<NewsResponse>> {
        return flow {
            val response = apiService.getTopNews()
            emit(ApiResponse.Success(response))
        }.flowOn(Dispatchers.IO)
    }

}