package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.plans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans.DailyInfoDto
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.TickersRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.plans.DailyRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
  private val repository: DailyRepository,
  private val tickersRepository: TickersRepository
) : BaseViewModel() {
  private val _plansPaginate
      = MutableLiveData<ApiResource<DtoPaginate<DailyInfoDto>>>()
  val plansPaginate: LiveData<ApiResource<DtoPaginate<DailyInfoDto>>>
    get() = _plansPaginate

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
        _plansPaginate.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _plansPaginate.postValue(ApiResource.Error(ApiError(500, message)))
        }
      }.collect { response ->
        response.data.let {
          _plansPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }
}