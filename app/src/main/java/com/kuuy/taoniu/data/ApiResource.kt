package com.kuuy.taoniu.data

sealed class ApiResource<T>(
  val data: T? = null,
  val code: Int = 200,
  val message: String? = null
) {
  class Success<T>(data: T?) : ApiResource<T>(data)
  class Error<T>(
    message: String?,
    code: Int = 0,
    data: T? = null
  ) : ApiResource<T>(data, code, message)
  class Loading<T> : ApiResource<T>()
}

