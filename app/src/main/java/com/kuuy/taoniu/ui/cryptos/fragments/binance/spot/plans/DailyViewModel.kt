package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.plans

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans.DailyInfoDto
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.TickersRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.plans.DailyRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class DailyViewModel @Inject constructor(
  private val repository: DailyRepository,
  private val tickersRepository: TickersRepository
) : BaseViewModel() {
  private val _analysisPaginate
      = MutableLiveData<ApiResource<DtoPaginate<DailyInfoDto>>>()
  val analysisPaginate: LiveData<ApiResource<DtoPaginate<DailyInfoDto>>>
    get() = _analysisPaginate

  fun listings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      repository.listings(
        current,
        pageSize,
      ).onStart {
        _analysisPaginate.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _analysisPaginate.postValue(ApiResource.Error(message))
        }
      }.collect { response ->
        response.data.let {
          _analysisPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }
}