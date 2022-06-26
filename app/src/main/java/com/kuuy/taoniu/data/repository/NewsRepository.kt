package com.kuuy.taoniu.data.repository

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.model.NewsResponse
import com.kuuy.taoniu.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource
) {

  suspend fun getTopNews(): Flow<ApiResource<NewsResponse>> = flow {
    emit(ApiResource.Loading())
    when (val response = remoteDataSource.getTopNews().first()) {
      is ApiResponse.Success -> {
        val data = response.data
        emit(ApiResource.Success(data))
      }
      is ApiResponse.Empty -> {
        emit(ApiResource.Success(null))
      }
      is ApiResponse.Error -> {
        emit(ApiResource.Error(response.errorMessage))
      }
    }
  }

}
