package com.kuuy.taoniu.data

sealed class ApiResource<T>(
  val data: T? = null,
  val apiError: ApiError? = null,
) {
  class Success<T>(data: T?) : ApiResource<T>(data)
  class Error<T>(
    apiError: ApiError
  ) : ApiResource<T>(apiError=apiError)
  class Loading<T> : ApiResource<T>()
}
