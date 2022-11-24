package com.kuuy.taoniu.ui.cryptos.fragments.tradingview

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.TickersRepository
import com.kuuy.taoniu.data.cryptos.repositories.tradingview.AnalysisRepository
import com.kuuy.taoniu.di.PreferencesModule
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AnalysisViewModel @Inject constructor(
  private val repository: AnalysisRepository,
  private val tickersRepository: TickersRepository
) : BaseViewModel() {
  private val _tickers: MutableMap<String, MutableLiveData<String>> = mutableMapOf()
  val tickers: Map<String, MutableLiveData<String>>
    get() = _tickers

  private val _analysisPaginate
      = MutableLiveData<ApiResource<DtoPaginate<AnalysisInfoDto>>>()
  val analysisPaginate: LiveData<ApiResource<DtoPaginate<AnalysisInfoDto>>>
    get() = _analysisPaginate

  fun flushTickers() {
    var symbols = tickers.keys.toList()
    var fields = listOf("price")

    if (symbols.isEmpty()) {
      return
    }

    viewModelScope.launch {
      tickersRepository.gets(symbols, fields).collect { response ->
        response.data?.let {
          it.data.forEachIndexed { i, values ->
            val symbol = symbols[i]
            values.split(",").forEachIndexed { j, value ->
              if (fields[j] == "price") {
                _tickers[symbol]!!.postValue(value)
              }
            }
          }
        }
      }
    }
  }

  fun listings(
    exchange: String,
    interval: String,
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      repository.listings(
        exchange,
        interval,
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
          it?.data?.forEach { item ->
            _tickers[item.symbol] = MutableLiveData<String>()
          }
          _analysisPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }

}