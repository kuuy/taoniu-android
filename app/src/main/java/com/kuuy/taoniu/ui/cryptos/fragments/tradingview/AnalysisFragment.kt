package com.kuuy.taoniu.ui.cryptos.fragments.tradingview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.tradingview.transform
import com.kuuy.taoniu.databinding.FragmentCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.tradingview.AnalysisAdapter
import com.kuuy.taoniu.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : BaseFragment<FragmentCryptosTradingviewAnalysisBinding>() {
  private lateinit var mainHandler: Handler
  private val viewModel by viewModels<AnalysisViewModel>()
  private val adapter by lazy { AnalysisAdapter() }
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
    initRecycler()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      binding.swipeRefreshLayout.isRefreshing = false
    }
    mainHandler = Handler(Looper.getMainLooper())
  }

  override fun onResume() {
    super.onResume()
    mainHandler.post(flushAnalysis)
  }

  override fun onPause() {
    super.onPause()
    mainHandler.removeCallbacks(flushAnalysis)
  }

  private val flushAnalysis = object : Runnable {
    override fun run() {
    }
  }

  private val flushTickers = object : Runnable {
    override fun run() {
      //mainHandler.postDelayed(this, 1000)
    }
  }

  private fun initRecycler() {
    viewModel.listings("BINANCE", "1m", current, pageSize)
    binding.rvListings.apply {
      adapter = this@AnalysisFragment.adapter
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
      viewModel.listings("BINANCE", "1m", current+1, pageSize)
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

  private fun showLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}
