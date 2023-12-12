package com.kuuy.taoniu.ui.cryptos.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.dto.OrderListingsDto
import com.kuuy.taoniu.data.cryptos.repositories.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.kuuy.taoniu.ui.base.BaseViewModel

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository
) : BaseViewModel() {

  private val _orderListings
      = MutableLiveData<ApiResource<OrderListingsDto>>()
  val orderListings: LiveData<ApiResource<OrderListingsDto>> get()
      = _orderListings

  fun getOrderListings() {
    viewModelScope.launch {
      repository.getOrderListings()
          .onStart {
            _orderListings.postValue(ApiResource.Loading())
          }.catch {
            it.message?.let { message ->
              _orderListings.postValue(ApiResource.Error(ApiError(500, message)))
              retryFunctionList.add(::getOrderListings)
            }
          }.collect { response ->
            response.data.let {
              _orderListings.postValue(ApiResource.Success(it))
            }
          }
    }
  }
}
