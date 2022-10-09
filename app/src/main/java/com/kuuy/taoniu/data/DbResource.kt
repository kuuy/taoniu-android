package com.kuuy.taoniu.data

sealed class DbResource<T>(
  val data: T? = null,
  val message: String? = null
) {
  class Success<T>(data: T?) : DbResource<T>(data)
  class Error<T>(
    message: String?,
    data: T? = null
  ) : DbResource<T>(data, message)
  class Loading<T> : DbResource<T>()
}

