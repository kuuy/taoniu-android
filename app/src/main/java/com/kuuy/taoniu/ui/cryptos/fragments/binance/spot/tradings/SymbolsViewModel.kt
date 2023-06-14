package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.tradings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.dto.tradingview.AnalysisInfoDto
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.TickersRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.TradingsRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.tradings.FishersRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.margin.isolated.TradingsRepository as MarginIsolatedTradingsRepository
import com.kuuy.taoniu.data.cryptos.repositories.tradingview.AnalysisRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class SymbolsViewModel @Inject constructor(
  private val tradingsRepository: TradingsRepository,
  private val marginIsolatedTradingsRepository: MarginIsolatedTradingsRepository,
  private val tickersRepository: TickersRepository,
  private val analysisRepository: AnalysisRepository,
  private val fishersRepository: FishersRepository,
) : BaseViewModel() {
  private val _tickers: MutableMap<String, Ticker> = mutableMapOf()
  val tickers: Map<String, Ticker>
    get() = _tickers

  private val _analysisListings
      = MutableLiveData<ApiResource<DtoResponse<List<AnalysisInfoDto>>>>()
  val analysisListings: LiveData<ApiResource<DtoResponse<List<AnalysisInfoDto>>>>
    get() = _analysisListings

  fun scan(callback: () -> Unit) {
    var scene = "spot"
    viewModelScope.launch {
      tradingsRepository.scan().onStart {
        _tickers.clear()
      }.catch {
      }.collect { response ->
        response.data?.let {
          it?.data?.let { symbols ->
            fishersRepository.addAll(scene, symbols.toTypedArray())
          }
          it?.data?.forEach { symbol ->
            if (!_tickers.containsKey(symbol)) {
              _tickers[symbol] = Ticker(0f, 0f, 0f, 0f, 0f,0f,0, 0f, 0)
            }
          }
        }
        callback()
      }
    }
  }

  fun scanMarginIsolated(callback: () -> Unit) {
    var scene = "margin-isolated"
    viewModelScope.launch {
      marginIsolatedTradingsRepository.scan().onStart {
        _tickers.clear()
      }.catch {
      }.collect { response ->
        response.data?.let {
          it?.data?.let { symbols ->
            fishersRepository.addAll(scene, symbols.toTypedArray())
          }
          it?.data?.forEach { symbol ->
            if (!_tickers.containsKey(symbol)) {
              _tickers[symbol] = Ticker(0f, 0f, 0f, 0f, 0f,0f,0, 0f, 0)
            }
          }
        }
        callback()
      }
    }
  }

  fun analysis(
    exchange: String,
    interval: String,
    callback: () -> Unit,
  ) {
    var symbols = tickers.keys.toList()
    if (symbols.isEmpty()) {
      return
    }

    viewModelScope.launch {
      analysisRepository.gets(exchange, symbols, interval).onStart {
        _analysisListings.postValue(ApiResource.Loading())
      }.catch {
        it.message?.let { message ->
          _analysisListings.postValue(ApiResource.Error(message))
        }
      }.collect { response ->
        response.data?.let {
          callback()
          _analysisListings.postValue(ApiResource.Success(it))
        }
      }
    }
  }

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
            if (data.size != fields.size) {
              return@forEachIndexed
            }
            _tickers[symbol]?.let {ticker ->
              fields.forEachIndexed { j, field ->
                if (data[j].isEmpty()) {
                  return@forEachIndexed
                }
                if (field == "open") {
                  ticker.open = data[j].toFloat()
                } else if (field == "price") {
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
              var change = round((ticker.price - ticker.open)*10000/ticker.open) /100
              if (change > ticker.change) {
                ticker.changeState = 1
              } else if (change > ticker.change) {
                ticker.changeState = 2
              } else {
                ticker.changeState = 0
              }
              ticker.change = change
              tickersRepository.save(symbol, ticker)
            }
          }
          callback()
        }
      }
    }
  }
}