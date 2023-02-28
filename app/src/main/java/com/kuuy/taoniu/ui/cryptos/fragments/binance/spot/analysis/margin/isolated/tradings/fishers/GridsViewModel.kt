package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.analysis.margin.isolated.tradings.fishers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.margin.isolated.tradings.fishers.GridInfoDto
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.analysis.margin.isolated.tradings.fishers.GridsRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridsViewModel @Inject constructor(
  private val repository: GridsRepository,
): BaseViewModel() {
  private val _gridsPaginate
      = MutableLiveData<ApiResource<DtoPaginate<GridInfoDto>>>()
  val gridsPaginate: LiveData<ApiResource<DtoPaginate<GridInfoDto>>>
    get() = _gridsPaginate

  private val _series
      = MutableLiveData<ApiResource<DtoResponse<List<ArrayList<Any>>>>>()
  val series: LiveData<ApiResource<DtoResponse<List<ArrayList<Any>>>>>
    get() = _series

  fun getSeries() {
    viewModelScope.launch {
      repository.series(10).onStart {
        _series.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _series.postValue(ApiResource.Error(message))
        }
      }.collect { response ->
        response.data.let {
          _series.postValue(ApiResource.Success(it))
        }
      }
    }
  }

  fun listings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      repository.listings(
        current,
        pageSize,
      ).onStart {
        _gridsPaginate.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _gridsPaginate.postValue(ApiResource.Error(message))
        }
      }.collect { response ->
        response.data.let {
          _gridsPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }
}