package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.margin.isolated.tradings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.isolated.tradings.GridInfoDto
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.margin.isolated.tradings.GridsRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridsViewModel @Inject constructor(
  private val repository: GridsRepository,
) : BaseViewModel() {
  private val _gridsPaginate
      = MutableLiveData<ApiResource<DtoPaginate<GridInfoDto>>>()
  val gridsPaginate: LiveData<ApiResource<DtoPaginate<GridInfoDto>>>
    get() = _gridsPaginate

  fun listings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      repository.listings(
        listOf(),
        current,
        pageSize,
      ).onStart {
        _gridsPaginate.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _gridsPaginate.postValue(ApiResource.Error(ApiError(500, message)))
        }
      }.collect { response ->
        response.data.let {
          _gridsPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }
}