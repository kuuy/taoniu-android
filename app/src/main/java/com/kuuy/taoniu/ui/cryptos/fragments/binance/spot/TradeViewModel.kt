package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.SymbolInfoDto
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.OrderInfoDto
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.plans.DailyInfoDto as PlanInfoDto
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.isolated.tradings.GridInfoDto
import com.kuuy.taoniu.data.cryptos.mappings.tradingview.transform
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisSummary
import com.kuuy.taoniu.data.cryptos.repositories.currencies.AboutRepository
import com.kuuy.taoniu.data.cryptos.repositories.tradingview.AnalysisRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.SymbolsRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.TickersRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.KlinesRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.margin.OrdersRepository
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.plans.DailyRepository as PlansReposioty
import com.kuuy.taoniu.data.cryptos.repositories.binance.spot.margin.isolated.tradings.GridsRepository
import com.kuuy.taoniu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class TradeViewModel @Inject constructor(
  private val aboutRepository: AboutRepository,
  private val analysisRepository: AnalysisRepository,
  private val symbolsRepository: SymbolsRepository,
  private val tickersRepository: TickersRepository,
  private val klinesRepository: KlinesRepository,
  private val ordersRepository: OrdersRepository,
  private val plansRepository: PlansReposioty,
  private val gridsRepository: GridsRepository,
) : BaseViewModel() {
  private val _symbolInfo = MutableLiveData<ApiResource<SymbolInfoDto>>()
  val symbolInfo: LiveData<ApiResource<SymbolInfoDto>>
    get() = _symbolInfo

  private val _about = MutableLiveData<ApiResource<String>>()
  val about: LiveData<ApiResource<String>>
    get() = _about

  private var _summaryInfo = AnalysisSummary(0, 0, 0, "--")
  val summaryInfo: AnalysisSummary
    get() = _summaryInfo

  private val _tickers: MutableMap<String, Ticker> = mutableMapOf()
  val tickers: Map<String, Ticker>
    get() = _tickers

  private val _slippages: MutableMap<String, Float> = mutableMapOf()
  val slippages: Map<String, Float>
    get() = _slippages

  var symbol = ""
    set(value) { field=value; _tickers[symbol] = Ticker(0f, 0f, 0f, 0f, 0f, 0f, 0, 0f, 0) }

  private val _series
      = MutableLiveData<ApiResource<List<FloatArray>>>()
  val series: LiveData<ApiResource<List<FloatArray>>>
    get() = _series

  private val _ordersPaginate
      = MutableLiveData<ApiResource<DtoPaginate<OrderInfoDto>>>()
  val ordersPaginate: LiveData<ApiResource<DtoPaginate<OrderInfoDto>>>
    get() = _ordersPaginate

  private val _plansPaginate
      = MutableLiveData<ApiResource<DtoPaginate<PlanInfoDto>>>()
  val plansPaginate: LiveData<ApiResource<DtoPaginate<PlanInfoDto>>>
    get() = _plansPaginate

  private val _gridsPaginate
      = MutableLiveData<ApiResource<DtoPaginate<GridInfoDto>>>()
  val gridsPaginate: LiveData<ApiResource<DtoPaginate<GridInfoDto>>>
    get() = _gridsPaginate

  fun getAbout(currency: String) {
    viewModelScope.launch {
      aboutRepository.get(currency)
        .onStart {
          _about.postValue(ApiResource.Loading())
        }.catch {
          it.message?.let {}
        }.collect { response ->
          response.data?.let {
            _about.postValue(ApiResource.Success(it))
          }
        }
    }
  }

  fun getSymbolInfo() {
    viewModelScope.launch {
      symbolsRepository.get(symbol)
        .onStart {
          _symbolInfo.postValue(ApiResource.Loading())
        }.catch {
          it.message?.let {}
        }.collect { response ->
          response.data?.let {
            _symbolInfo.postValue(ApiResource.Success(it))
          }
        }
    }
  }

  fun flushTickers(callback: () -> Unit) {
    var symbols = tickers.keys.toList()
    var fields = listOf("open","price", "high", "low", "volume", "quota")

    if (symbols.isEmpty()) {
      return
    }

    viewModelScope.launch {
      tickersRepository.gets(symbols, fields).collect { response ->
        response.data?.let {
          it.forEachIndexed { i, values ->
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
                } else if (field == "high") {
                  ticker.high = data[j].toFloat()
                } else if (field == "low") {
                  ticker.low = data[j].toFloat()
                } else if (field == "volume") {
                  ticker.volume = data[j].toFloat()
                } else if (field == "quota") {
                  ticker.quota = data[j].toFloat()
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
            }
          }
          callback()
        }
      }
    }
  }

  fun flushSlippages(callback: () -> Unit) {
    val fields = listOf("slippage@1%","slippage@-1%","slippage@2%","slippage@-2%")

    viewModelScope.launch {
      tickersRepository.gets(listOf(symbol), fields).collect { response ->
        response.data?.let {
          it.forEachIndexed { _, values ->
            val data = values.split(",")
            if (data.size != fields.size) {
              return@forEachIndexed
            }
            fields.forEachIndexed { i, field ->
              if (data[i].isEmpty()) {
                return@forEachIndexed
              }
              _slippages[field] = data[i].toFloat()
            }
          }
          callback()
        }
      }
    }
  }

  fun flushSummary(callback: () -> Unit) {
    viewModelScope.launch {
      analysisRepository.summary("BINANCE", symbol, "1m").collect { response ->
        response.data?.let {
          _summaryInfo = it.transform()
          callback()
        }
      }
    }
  }

  fun getSeries() {
    viewModelScope.launch {
      klinesRepository.series(symbol, "1d", 100)
        .catch {
          it.message?.let {}
        }.collect { response ->
          response.data.let {
            _series.postValue(ApiResource.Success(it))
          }
        }
    }
  }

  fun ordersListings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      ordersRepository.listings(
        listOf(symbol),
        current,
        pageSize,
      ).catch {
        it.message?.let {}
      }.collect { response ->
        response.data.let {
          _ordersPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }

  fun plansListings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      plansRepository.listings(
        listOf(symbol),
        current,
        pageSize,
      ).catch {
        it.message?.let {}
      }.collect { response ->
        response.data.let {
          _plansPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }

  fun gridsListings(
    current: Int,
    pageSize: Int,
  ) {
    viewModelScope.launch {
      gridsRepository.listings(
        listOf(symbol),
        current,
        pageSize,
      ).catch {
        it.message?.let {}
      }.collect { response ->
        response.data.let {
          _gridsPaginate.postValue(ApiResource.Success(it))
        }
      }
    }
  }
}