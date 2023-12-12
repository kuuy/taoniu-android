package com.kuuy.taoniu.data

sealed class ApiResponse<out T> {
  data class Success<out T>(val data: T) : ApiResponse<T>()
  data class Error(val apiError: ApiError) : ApiResponse<Nothing>()
  object Empty : ApiResponse<Nothing>()
}
