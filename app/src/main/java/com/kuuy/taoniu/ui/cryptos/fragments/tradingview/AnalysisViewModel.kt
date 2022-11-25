package com.kuuy.taoniu.ui.cryptos.fragments.tradingview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.TickersRepository
import com.kuuy.taoniu.data.cryptos.repositories.tradingview.AnalysisRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
  private val repository: AnalysisRepository,
  private val tickersRepository: TickersRepository
) : BaseViewModel() {
  private val _tickers: MutableMap<String, TickerInfo> = mutableMapOf()
  val tickers: Map<String, TickerInfo>
    get() = _tickers

  private val _analysisPaginate
      = MutableLiveData<ApiResource<DtoPaginate<AnalysisInfoDto>>>()
  val analysisPaginate: LiveData<ApiResource<DtoPaginate<AnalysisInfoDto>>>
    get() = _analysisPaginate

  fun flushTickers(callback: () -> Unit) {
    var symbols = tickers.keys.toList()
    var fields = listOf("open","price")

    if (symbols.isEmpty()) {
      return
    }

    viewModelScope.launch {
      tickersRepository.gets(symbols, fields).collect { response ->
        response.data?.let {
          it.data.forEachIndexed { i, values ->
            val symbol = symbols[i]
            val data = values.split(",")
            fields.forEachIndexed { j, field ->
              if (field == "open" && data[j].isNotEmpty()) {
                _tickers[symbol]?.open = data[j].toFloat()
              } else if (field == "price" && data[j].isNotEmpty()) {
                _tickers[symbol]?.let {ticker ->
                  var price = data[j].toFloat()
                  if (price > ticker.price) {
                    ticker.state = 1
                  } else if (price < ticker.price) {
                    ticker.state = 2
                  } else {
                    ticker.state = 0
                  }
                  ticker.price = price
                }
              }
            }
          }
          callback.invoke()
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
            if (!_tickers.containsKey(item.symbol)) {
              _tickers[item.symbol] = TickerInfo(0f, 0f, 0)
            }
          }
          _analysisPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }
}