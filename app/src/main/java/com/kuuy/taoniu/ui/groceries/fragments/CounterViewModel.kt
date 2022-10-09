package com.kuuy.taoniu.ui.groceries.fragments

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.groceries.models.CounterOrderListings
import com.kuuy.taoniu.data.groceries.models.CounterOrderInfo
import com.kuuy.taoniu.data.groceries.repositories.CounterRepository
import com.kuuy.taoniu.ui.base.BaseViewModel

@HiltViewModel
class CounterViewModel @Inject constructor(
  private val repository: CounterRepository
) : BaseViewModel() {

  private val _orderListings
      = MutableLiveData<DbResource<CounterOrderListings>>()
  private val _counterClear
      = MutableLiveData<DbResource<Nothing>>()
  var subTotalAmount : Float = 0.0f
  var discount : Float = 0.0f
  var discountAmount : Float = 0.0f
  var reduceAmount : Float = 0.0f
  var settlementAmount : Float = 0.0f
  val orderListings: LiveData<DbResource<CounterOrderListings>>
      get() = _orderListings
  val counterClear: LiveData<DbResource<Nothing>>
      get() = _counterClear

  fun getOrderListings() {
    viewModelScope.launch {
      repository.getOrderListings()
          .onStart {
            _orderListings.postValue(DbResource.Loading())
          }.catch {
            it.message?.let { message ->
              _orderListings.postValue(DbResource.Error(message))
            }
          }.collect { result ->
            result.data?.let {
              _orderListings.postValue(DbResource.Success(it))
            }
          }
    }
  }

  fun removeOrder(id: Long) {
    viewModelScope.launch {
      repository.removeOrder(id)
          .onStart {
            _counterClear.postValue(DbResource.Loading())
          }.catch {
            it.message?.let { message ->
              _counterClear.postValue(DbResource.Error(message))
            }
          }.collect { result ->
            result.data.let {
              _counterClear.postValue(DbResource.Success(it))
            }
         }
    }
  }

  fun clear() {
    viewModelScope.launch {
      repository.clear()
          .onStart {
            _counterClear.postValue(DbResource.Loading())
          }.catch {
            it.message?.let { message ->
              _counterClear.postValue(DbResource.Error(message))
            }
          }.collect { result ->
            result.data.let {
              _counterClear.postValue(DbResource.Success(it))
            }
         }
    }
  }

  fun settlement(orders: List<CounterOrderInfo>) {
    subTotalAmount = 0.0f
    for (order in orders) {
      subTotalAmount += order.price * order.quantity
    }
    settlementAmount = subTotalAmount
  }
}

