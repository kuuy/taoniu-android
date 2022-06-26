package com.kuuy.taoniu.ui.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
  protected var retryFunctionList: MutableList<() -> Unit> =
      mutableListOf()

  protected fun retryFailed() {
    val currentList = retryFunctionList.toList()
    retryFunctionList.clear()
    currentList.forEach {
      it.invoke()
    }
  } 
}
