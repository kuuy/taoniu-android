package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.margin.isolated.tradings.transform
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.margin.transform
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.plans.transform
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.transform
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotTradeBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin.OrdersAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin.isolated.tradings.GridsAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.trade.TabPagerAdapter
import com.kuuy.taoniu.utils.*
import com.tradingview.lightweightcharts.api.chart.models.color.toIntColor
import com.tradingview.lightweightcharts.api.options.models.*
import com.tradingview.lightweightcharts.api.series.enums.LineStyle
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.tradingview.lightweightcharts.api.series.models.Time
import com.tradingview.lightweightcharts.runtime.plugins.DateTimeFormat
import com.tradingview.lightweightcharts.runtime.plugins.PriceFormatter
import com.tradingview.lightweightcharts.runtime.plugins.TimeFormatter
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme
import java.util.*
import kotlin.math.ceil
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.plans.DailyAdapter as PlansAdapter

@AndroidEntryPoint
class TradeFragment : BaseFragment<FragmentCryptosBinanceSpotTradeBinding>() {
  private val args by navArgs<TradeFragmentArgs>()
  private val viewModel by viewModels<TradeViewModel>()
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
  private val pagerAdapter by lazy { TabPagerAdapter(::initRecyclerView, ::initTextView) }
  private val ordersAdapter by lazy { OrdersAdapter{} }
  private val plansAdapter by lazy { PlansAdapter{} }
  private val gridsAdapter by lazy { GridsAdapter{} }
  private val markdown by lazy {
    Markwon.builder(requireContext()).usePlugin(
      object: AbstractMarkwonPlugin(){
        override fun configureTheme(builder: MarkwonTheme.Builder) {
          builder
            .codeTextColor(Color.BLACK)
            .codeBackgroundColor(Color.GREEN)
        }
      }
    ).build()
  }
  private var about = ""
  private val tabs = arrayOf("订单", "计划", "网格", "详情")
  private var isLoading = false
  private var current = 1
  private val pageSize = 20

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotTradeBinding {
    return FragmentCryptosBinanceSpotTradeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onResume() {
    super.onResume()
    mainHandler.post(flushSummary)
    mainHandler.post(flushTickers)
    mainHandler.post(flushSlippages)
  }

  override fun onPause() {
    super.onPause()
    mainHandler.removeCallbacks(flushSummary)
    mainHandler.removeCallbacks(flushTickers)
    mainHandler.removeCallbacks(flushSlippages)
  }

  private val flushTickers: Runnable by lazy {
    Runnable {
      if (!isLoading) {
        viewModel.flushTickers(){
          viewModel.tickers[args.symbol]?.let { ticker ->
            binding.tvPrice.text = ticker.price.toString()
            binding.lbHigh.value = ticker.high
            binding.lbLow.value = ticker.low
            if (ticker.change > 0) {
              binding.tvPercent.let{
                it.text ="+%.2f%%".format(ticker.change)
                it.setTextColor(it.context.getColor(R.color.material_green300))
              }
            } else {
              binding.tvPercent.let{
                if (ticker.change < 0) {
                  it.text ="%.2f%%".format(ticker.change)
                  it.setTextColor(it.context.getColor(R.color.material_red300))
                } else {
                  it.text = "0.00%"
                  it.setTextColor(it.context.getColor(R.color.material_grey300))
                }
              }
            }
          }
        }
      }
      mainHandler.postDelayed(flushTickers, 5000)
    }
  }

  private val flushSlippages: Runnable by lazy {
    Runnable {
      viewModel.flushSlippages(){
        var percent01 = ceil(viewModel.slippages["slippage@1%"]!!*100 /
            viewModel.slippages["slippage@1%"]!!.plus(viewModel.slippages["slippage@-1%"]!!)) / 100
        var percent02 = ceil(viewModel.slippages["slippage@2%"]!!*100 /
            viewModel.slippages["slippage@2%"]!!.plus(viewModel.slippages["slippage@-2%"]!!)) / 100

        binding.tvSlippageAsks01.apply{
          text = viewModel.slippages["slippage@1%"]!!.format()
          (layoutParams as ConstraintLayout.LayoutParams).apply {
            matchConstraintPercentWidth = percent01
          }
          requestLayout()
        }

        binding.tvSlippageBids01.apply{
          text = viewModel.slippages["slippage@-1%"]!!.format()
          (layoutParams as ConstraintLayout.LayoutParams).apply {
            matchConstraintPercentWidth = 1 - percent01
          }
          requestLayout()
        }

        binding.tvSlippageAsks02.apply{
          text = viewModel.slippages["slippage@2%"]!!.format()
          (layoutParams as ConstraintLayout.LayoutParams).apply {
            matchConstraintPercentWidth = percent02
          }
          requestLayout()
        }

        binding.tvSlippageBids02.apply{
          text = viewModel.slippages["slippage@-2%"]!!.format()
          (layoutParams as ConstraintLayout.LayoutParams).apply {
            matchConstraintPercentWidth = 1 - percent02
          }
          requestLayout()
        }
      }
      mainHandler.postDelayed(flushSlippages, 5000)
    }
  }

  private val flushSummary: Runnable by lazy {
    Runnable {
      viewModel.flushSummary(){
        binding.tvSummaryBuy.text = viewModel.summaryInfo.buy.toString()
        binding.tvSummarySell.text = viewModel.summaryInfo.sell.toString()
        binding.tvSummaryNeutral.text = viewModel.summaryInfo.neutral.toString()
        binding.tvSummaryRecommendation.apply{
          text = viewModel.summaryInfo.recommendation
          if (viewModel.summaryInfo.recommendation == "BUY" || viewModel.summaryInfo.recommendation == "STRONG_BUY") {
            setBackgroundColor(context.getColor(R.color.material_green300))
          } else if (viewModel.summaryInfo.recommendation == "SELL" || viewModel.summaryInfo.recommendation == "STRONG_SELL") {
            setBackgroundColor(context.getColor(R.color.material_red300))
          } else {
            setBackgroundColor(context.getColor(R.color.material_grey300))
          }
        }
      }
      mainHandler.postDelayed(flushSummary, 5000)
    }
  }

  private fun initTabPager() {
    binding.pager.adapter = pagerAdapter
    binding.pager.tabs(tabs, TabLayout.MODE_FIXED, viewLifecycleOwner)
  }

  private fun initRecyclerView(rvListings: RecyclerView, position: Int) {
    when (position) {
      0 -> {
        ordersAdapter.clear()
        viewModel.ordersListings(1, pageSize)
      }
      1 -> {
        plansAdapter.clear()
        viewModel.plansListings(1, pageSize)
      }
      2 -> {
        gridsAdapter.clear()
        viewModel.gridsListings(1, pageSize)
      }
    }

    rvListings.apply {
      layoutManager ?: run {
        layoutManager = LinearLayoutManager(context)
        val divider = DividerItemDecoration(
          context,
          DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
          context,
          R.drawable.divider_transparent
        )?.let {
          divider.setDrawable(it)
        }
        addItemDecoration(divider)
        setHasFixedSize(true)
      }
      when (position) {
        0 -> {
          adapter = this@TradeFragment.ordersAdapter
          addOnScrollListener(OnScrollListener(layoutManager as LinearLayoutManager) {
            viewModel.ordersListings( current+1, pageSize)
          })
        }
        1 -> {
          adapter = this@TradeFragment.plansAdapter
          addOnScrollListener(OnScrollListener(layoutManager as LinearLayoutManager) {
            viewModel.plansListings( current+1, pageSize)
          })
        }
        2 -> {
          adapter = this@TradeFragment.gridsAdapter
          addOnScrollListener(OnScrollListener(layoutManager as LinearLayoutManager) {
            viewModel.gridsListings( current+1, pageSize)
          })
        }
      }
    }
  }

  private fun initTextView(textView: TextView, position: Int) {
    markdown.setMarkdown(textView, about)
  }

  private fun initKlineChart() {
    binding.klineChart.api.applyOptions {
      layout = layoutOptions {
        textColor = R.color.primary_text.toIntColor()
      }
      rightPriceScale = priceScaleOptions {
        visible = false
      }
      timeScale = timeScaleOptions {
        visible = false
      }
      grid = gridOptions {
        vertLines = gridLineOptions {
          color = R.color.material_grey300.toIntColor()
          style = LineStyle.SPARSE_DOTTED
        }
        horzLines = gridLineOptions {
          color = R.color.material_grey300.toIntColor()
          style = LineStyle.SPARSE_DOTTED
        }
      }
      handleScroll = handleScrollOptions {
        vertTouchDrag = false
      }
      localization = localizationOptions {
        locale = Locale.getDefault().toLanguageTag()
        priceFormatter = PriceFormatter(template = "\${price}")
        timeFormatter = TimeFormatter(
          locale = Locale.getDefault().toLanguageTag(),
          dateTimeFormat = DateTimeFormat.DATE_TIME
        )
      }
    }
  }

  override fun onBind() {
    binding.ivBack.setOnClickListener{
      findNavController().popBackStack()
    }
    initKlineChart()
    initTabPager()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
    }
  }

  private fun initViewModel() {
    viewModel.symbol = args.symbol
    viewModel.getSymbolInfo()
    viewModel.symbolInfo.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val symbolInfo = it.transform()
            binding.tvTitle.text = "${symbolInfo.baseAsset}/${symbolInfo.quoteAsset}"
            viewModel.getAbout(symbolInfo.baseAsset)
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }
    viewModel.about.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
        }
        is ApiResource.Success -> {
          response.data?.let {
            about = it
          }
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
        }
      }
    }
    viewModel.getSeries()
    viewModel.series.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            var series: MutableList<BarData> = mutableListOf()
            it.forEach{ item ->
              series.add(0, BarData(
                Time.Utc(item[4].toLong() / 1000),
                item[0],
                item[1],
                item[2],
                item[3],
              ))
            }
            binding.klineChart.api.addCandlestickSeries(
              options = candlestickSeriesOptions {
                wickUpColor = R.color.material_green300.toIntColor()
//                upColor = R.color.material_green300.toIntColor()
                wickDownColor = R.color.material_red300.toIntColor()
//                downColor = R.color.material_red300.toIntColor()
              },
              onSeriesCreated = { api ->
                api.setData(series)
              }
            )
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }
    viewModel.ordersPaginate.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val orders = it.data.transform()
            ordersAdapter.addDatas(orders)
            current = it.current
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }
    viewModel.plansPaginate.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val plans = it.data.transform()
            plansAdapter.addDatas(plans)
            current = it.current
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }
    viewModel.gridsPaginate.observe(
      viewLifecycleOwner
    ) { response ->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val grids = it.data.transform()
            gridsAdapter.addDatas(grids)
            current = it.current
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
  }
}