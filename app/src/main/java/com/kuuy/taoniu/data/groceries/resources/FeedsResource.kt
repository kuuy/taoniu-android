package com.kuuy.taoniu.data.groceries.resources

import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.groceries.api.FeedsApi
import com.kuuy.taoniu.data.groceries.dto.FeedInfoDto

class FeedsResource @Inject constructor(
  private var api: FeedsApi,
  private var gson: Gson,
) {
  suspend fun listings(
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResponse<DtoPaginate<FeedInfoDto>>> {
    return flow {
      val response = api.listings(current, pageSize)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }
}