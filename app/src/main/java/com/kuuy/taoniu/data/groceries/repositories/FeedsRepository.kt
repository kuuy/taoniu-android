package com.kuuy.taoniu.data.groceries.repositories

import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.groceries.dto.FeedInfoDto
import com.kuuy.taoniu.data.groceries.resources.FeedsResource
import kotlinx.coroutines.flow.onStart

class FeedsRepository @Inject constructor(
  private val resource: FeedsResource,
) {
  suspend fun listings(
    current: Int,
    pageSize: Int,
  ): Flow<ApiResource<DtoPaginate<FeedInfoDto>>> {
    return flow {
      resource.listings(current, pageSize).onStart {
        emit(ApiResource.Loading())
      }.catch {
        emit(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResponse.Success -> {
            val data = response.data
            emit(ApiResource.Success(data))
          }
          is ApiResponse.Error -> {
            emit(ApiResource.Error(response.apiError))
          }
          is ApiResponse.Empty -> {
            emit(ApiResource.Success(null))
          }
        }
      }
    }
  }
}