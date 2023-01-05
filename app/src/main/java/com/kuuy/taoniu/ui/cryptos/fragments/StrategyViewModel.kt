package com.kuuy.taoniu.ui.cryptos.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.dto.StrategyListingsDto
import com.kuuy.taoniu.data.cryptos.repositories.StrategyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.kuuy.taoniu.ui.base.BaseViewModel

@HiltViewModel
class StrategyViewModel @Inject constructor(
    private val repository: StrategyRepository
) : BaseViewModel() {

  private val _strategyListings
      = MutableLiveData<ApiResource<StrategyListingsDto>>()
  val strategyListings: LiveData<ApiResource<StrategyListingsDto>> get()
      = _strategyListings

  fun getStrategyListings() {
    viewModelScope.launch {
      repository.getStrategyListings()
          .onStart {
            _strategyListings.postValue(ApiResource.Loading())
          }.catch {
            it.message?.let { message ->
              _strategyListings.postValue(ApiResource.Error(message))
              retryFunctionList.add(::getStrategyListings)
            }
          }.collect { response ->
            response.data.let {
              _strategyListings.postValue(ApiResource.Success(it))
            }
          }
    }
  }
}
