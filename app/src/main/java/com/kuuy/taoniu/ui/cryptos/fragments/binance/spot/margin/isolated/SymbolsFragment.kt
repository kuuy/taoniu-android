package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.margin.isolated

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.tradingview.transform
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotMarginIsolatedSymbolsBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin.isolated.SymbolsAdapter
import com.kuuy.taoniu.ui.cryptos.fragments.tradingview.AnalysisFragmentDirections
import com.kuuy.taoniu.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SymbolsFragment : BaseFragment<FragmentCryptosBinanceSpotMarginIsolatedSymbolsBinding>() {
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
  private val viewModel by viewModels<SymbolsViewModel>()
  private val adapter by lazy { SymbolsAdapter(::ticker){ model ->
    val action = AnalysisFragmentDirections
      .toTrade(model.symbol)
    findNavController().navigate(action)
  } }
  private var isLoading = false

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotMarginIsolatedSymbolsBinding {
    return FragmentCryptosBinanceSpotMarginIsolatedSymbolsBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initSearchView()
    initRecyclerView()
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

  private fun initRecyclerView() {
    adapter.clear()
    viewModel.scan{
      viewModel.analysis("BINANCE", "1m"){
        adapter.clear()
      }
    }
    binding.rvListings.apply {
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
            val analysis = it.data.transform()
            adapter.addDatas(analysis)
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