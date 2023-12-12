package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.tradings

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotTradingsSymbolsBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.tradings.TabPagerAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.tradings.SymbolsAdapter
import com.kuuy.taoniu.ui.cryptos.fragments.tradingview.AnalysisFragmentDirections
import com.kuuy.taoniu.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SymbolsFragment : BaseFragment<FragmentCryptosBinanceSpotTradingsSymbolsBinding>() {
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
  private val viewModel by viewModels<SymbolsViewModel>()
  private val adapter by lazy { SymbolsAdapter(::ticker){ model ->
    val action = AnalysisFragmentDirections
      .toTrade(model.symbol)
    findNavController().navigate(action)
  } }
  private val pagerAdapter by lazy { TabPagerAdapter(::initRecyclerView) }
  private val tabs = arrayOf("现货", "杠杆")
  private var isLoading = false

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotTradingsSymbolsBinding {
    return FragmentCryptosBinanceSpotTradingsSymbolsBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initSearchView()
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

  private fun initSearchView() {
    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
      }
    })
  }

  private fun initTabPager() {
    binding.pager.adapter = pagerAdapter
    binding.pager.tabs(tabs, TabLayout.MODE_FIXED, viewLifecycleOwner)
  }

  private fun initRecyclerView(rvListings: RecyclerView, position: Int) {
    adapter.clear()
    when (position) {
      0 -> {
        viewModel.scan{
          viewModel.analysis("BINANCE", "1m"){
            adapter.clear()
          }
        }
      }
      1 -> {
        viewModel.scanMarginIsolated{
          viewModel.analysis("BINANCE", "1m"){
            adapter.clear()
          }
        }
      }
    }
    rvListings.apply {
      layoutManager ?: run {
        adapter = this@SymbolsFragment.adapter
        layoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(
          requireContext(),
          DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
          requireContext(),
          R.drawable.divider_transparent
        )?.let {
          divider.setDrawable(it)
        }
        addItemDecoration(divider)
        setHasFixedSize(true)
      }
    }
  }

  private fun initViewModel() {
    viewModel.analysisListings.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            val analysis = it.transform()
            adapter.addDatas(analysis)
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

  private fun ticker(symbol: String, callback: (Ticker) -> Unit) {
    viewModel.tickers[symbol]?.let{
      callback(it)
    }
  }

  private fun showLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}