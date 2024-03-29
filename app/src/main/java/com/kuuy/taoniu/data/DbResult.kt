package com.kuuy.taoniu.data

sealed class DbResult<out T> {
  data class Success<out T>(val data: T) : DbResult<T>()
  data class Error(val errorMessage: String) : DbResult<Nothing>()
  object Empty : DbResult<Nothing>()
}

