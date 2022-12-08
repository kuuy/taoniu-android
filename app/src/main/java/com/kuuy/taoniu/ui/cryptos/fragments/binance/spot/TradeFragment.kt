package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.TextView
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
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.margin.transform
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.plans.transform
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.transform
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.margin.isolated.tradings.transform
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotTradeBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin.isolated.tradings.GridsAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin.OrdersAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.plans.DailyAdapter as PlansAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.trade.TabPagerAdapter
import com.kuuy.taoniu.utils.OnScrollListener
import com.kuuy.taoniu.utils.*
import com.tradingview.lightweightcharts.api.chart.models.color.toIntColor
import com.tradingview.lightweightcharts.api.options.models.*
import com.tradingview.lightweightcharts.runtime.plugins.DateTimeFormat
import com.tradingview.lightweightcharts.runtime.plugins.PriceFormatter
import com.tradingview.lightweightcharts.runtime.plugins.TimeFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TradeFragment : BaseFragment<FragmentCryptosBinanceSpotTradeBinding>() {
  private val args by navArgs<TradeFragmentArgs>()
  private val viewModel by viewModels<TradeViewModel>()
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
  private val pagerAdapter by lazy { TabPagerAdapter(::initRecyclerView, ::initTextView) }
  private val ordersAdapter by lazy { OrdersAdapter{} }
  private val plansAdapter by lazy { PlansAdapter{} }
  private val gridsAdapter by lazy { GridsAdapter{} }
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
    mainHandler.post(flushTickers)
  }

  override fun onPause() {
    super.onPause()
    mainHandler.removeCallbacks(flushTickers)
  }

  private val flushTickers: Runnable by lazy {
    Runnable {
      if (!isLoading) {
        viewModel.flushTickers(){
          viewModel.tickers[args.symbol]?.let { ticker ->
            binding.tvPrice.text = ticker.price.toString()
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
  }

  private fun initKlineChart() {
    binding.klineChart.api.applyOptions {
      layout = layoutOptions {
        textColor = Color.BLACK.toIntColor()
      }
      grid = gridOptions {
        vertLines = gridLineOptions {
          color = Color.BLACK.toIntColor()
        }
        horzLines = gridLineOptions {
          color = Color.BLACK.toIntColor()
        }
      }
      handleScroll = handleScrollOptions {
        vertTouchDrag = false
      }
      localization = localizationOptions {
        locale = Locale.getDefault().toLanguageTag()
        priceFormatter = PriceFormatter(template = "{price:#2:#3}$")
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
            val symbolInfo = it.data.transform()
            binding.tvTitle.text = "${symbolInfo.baseAsset}/${symbolInfo.quoteAsset}"
          }
          showLoading(false)
        }
        is ApiResource.Error -> {
          showToast(response.message ?: "api error")
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
          showToast(response.message ?: "api error")
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
          showToast(response.message ?: "api error")
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
          showToast(response.message ?: "api error")
          showLoading(false)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
  }
}