package com.kuuy.taoniu.ui.base

import androidx.lifecycle.ViewModel
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.account.repositories.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
  protected var retryFunctionList: MutableList<() -> Unit> =
      mutableListOf()

  protected fun retryFailed() {
    val currentList = retryFunctionList.toList()
    retryFunctionList.clear()
    currentList.forEach {
      it()
    }
  }
}
