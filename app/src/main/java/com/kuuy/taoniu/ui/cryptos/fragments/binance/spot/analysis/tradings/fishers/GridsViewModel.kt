package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.analysis.tradings.fishers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.tradings.fishers.GridInfoDto
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.analysis.margin.isolated.tradings.fishers.GridInfoDto as MarginIsolatedGridInfoDto
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.analysis.tradings.fishers.GridsRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.analysis.margin.isolated.tradings.fishers.GridsRepository as MarginIsolatedGridsRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridsViewModel @Inject constructor(
  private val repository: GridsRepository,
  private val marginIsolatedRepository: MarginIsolatedGridsRepository,
): BaseViewModel() {
  private val _series
      = MutableLiveData<ApiResource<List<ArrayList<Any>>>>()
  val series: LiveData<ApiResource<List<ArrayList<Any>>>>
    get() = _series

  private val _gridsPaginate
      = MutableLiveData<ApiResource<DtoPaginate<GridInfoDto>>>()
  val gridsPaginate: LiveData<ApiResource<DtoPaginate<GridInfoDto>>>
    get() = _gridsPaginate

  private val _marginIsolatedSeries
      = MutableLiveData<ApiResource<List<ArrayList<Any>>>>()
  val marginIsolatedSeries: LiveData<ApiResource<List<ArrayList<Any>>>>
    get() = _marginIsolatedSeries

  private val _marginIsolatedGridsPaginate
      = MutableLiveData<ApiResource<DtoPaginate<MarginIsolatedGridInfoDto>>>()
  val marginIsolatedGridsPaginate: LiveData<ApiResource<DtoPaginate<MarginIsolatedGridInfoDto>>>
    get() = _marginIsolatedGridsPaginate

  fun getSeries() {
    viewModelScope.launch {
      repository.series(10).onStart {
        _series.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _series.postValue(ApiResource.Error(ApiError(500, message)))
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
      ).catch {
        _gridsPaginate.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _gridsPaginate.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _gridsPaginate.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _gridsPaginate.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }

  fun getMarginIsolatedSeries() {
    viewModelScope.launch {
      marginIsolatedRepository.series(10).catch {
        _marginIsolatedSeries.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _marginIsolatedSeries.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _marginIsolatedSeries.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _marginIsolatedSeries.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }

  fun marginIsolatedListings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      marginIsolatedRepository.listings(
        current,
        pageSize,
      ).catch {
        _marginIsolatedGridsPaginate.postValue(ApiResource.Success(null))
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {
            _marginIsolatedGridsPaginate.postValue(ApiResource.Loading())
          }
          is ApiResource.Success -> {
            response.data?.let {
              _marginIsolatedGridsPaginate.postValue(ApiResource.Success(it))
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              _marginIsolatedGridsPaginate.postValue(ApiResource.Error(it))
            }
          }
        }
      }
    }
  }
}