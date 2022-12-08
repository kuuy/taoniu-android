package com.kuuy.taoniu.ui.cryptos.fragments.tradingview

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.tradingview.transform
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.databinding.FragmentCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.tradingview.AnalysisAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.tradingview.TabPagerAdapter
import com.kuuy.taoniu.utils.*
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("NotifyDataSetChanged")
@AndroidEntryPoint
class AnalysisFragment : BaseFragment<FragmentCryptosTradingviewAnalysisBinding>() {
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
  private val viewModel by viewModels<AnalysisViewModel>()
  private val adapter by lazy { AnalysisAdapter(::ticker){ model ->
    val action = AnalysisFragmentDirections
      .toTrade(model.symbol)
    findNavController().navigate(action)
  } }
  private val pagerAdapter by lazy { TabPagerAdapter(::initRecyclerView) }
  private val tabs = arrayOf("1m", "5m", "15m", "30m", "1h", "2h", "4h", "1d", "1W", "1M")
  private var isLoading = false
  private var current = 1
  private val pageSize = 20

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosTradingviewAnalysisBinding {
    return FragmentCryptosTradingviewAnalysisBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initTabPager()
    initViewModel()
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
          adapter.notifyDataSetChanged()
        }
      }
      mainHandler.postDelayed(flushTickers, 5000)
    }
  }

  private fun initTabPager() {
    binding.pager.adapter = pagerAdapter
    binding.pager.tabs(tabs, TabLayout.MODE_SCROLLABLE, viewLifecycleOwner)
  }

  private fun initRecyclerView(rvListings: RecyclerView, position: Int) {
    adapter.clear()
    viewModel.listings("BINANCE", tabs[position], 1, pageSize)

    rvListings.apply {
      adapter = this@AnalysisFragment.adapter
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
      addOnScrollListener(OnScrollListener(layoutManager as LinearLayoutManager) {
        viewModel.listings("BINANCE", tabs[position], current+1, pageSize)
      })
    }
  }

  private fun initViewModel() {
    viewModel.analysisPaginate.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val analysis = it.data.transform()
            adapter.addDatas(analysis)
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

  private fun ticker(symbol: String, callback: (TickerInfo) -> Unit) {
    viewModel.tickers[symbol]?.let{
      callback(it)
    }
  }

  private fun showLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}
