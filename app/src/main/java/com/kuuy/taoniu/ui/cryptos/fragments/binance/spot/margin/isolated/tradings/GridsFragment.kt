package com.kuuy.taoniu.ui.cryptos.fragments.binance.spot.margin.isolated.tradings

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.binance.spot.margin.isolated.tradings.transform
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotMarginIsolatedTradingsGridsBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin.isolated.tradings.GridsAdapter
import com.kuuy.taoniu.ui.cryptos.fragments.tradingview.AnalysisFragmentDirections
import com.kuuy.taoniu.utils.OnScrollListener
import com.kuuy.taoniu.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GridsFragment : BaseFragment<FragmentCryptosBinanceSpotMarginIsolatedTradingsGridsBinding>() {
  private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
  private val viewModel by viewModels<GridsViewModel>()
  private val adapter by lazy { GridsAdapter{ model ->
    val action = GridsFragmentDirections
      .toTrade(model.symbol)
    findNavController().navigate(action)
  } }
  private var isLoading = false
  private var current = 1
  private val pageSize = 20

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotMarginIsolatedTradingsGridsBinding {
    return FragmentCryptosBinanceSpotMarginIsolatedTradingsGridsBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initRecycler()
    initViewModel()
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onPause() {
    super.onPause()
  }

  private val flushGrids = object : Runnable {
    override fun run() {
    }
  }

  private fun initRecycler() {
    viewModel.listings(current, pageSize)
    binding.rvListings.apply {
      adapter = this@GridsFragment.adapter
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
    val onScrollListener = OnScrollListener(binding.rvListings.layoutManager as LinearLayoutManager) {
      viewModel.listings(current+1, pageSize)
    }
    binding.rvListings.addOnScrollListener(onScrollListener)
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
            val grids = it.data.transform()
            adapter.addDatas(grids)
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
    this.isLoading = isLoading
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}