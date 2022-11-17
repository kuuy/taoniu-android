package com.kuuy.taoniu.ui.cryptos.fragments.tradingview

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.tradingview.transform
import com.kuuy.taoniu.databinding.FragmentCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.tradingview.AnalysisAdapter
import com.kuuy.taoniu.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : BaseFragment<FragmentCryptosTradingviewAnalysisBinding>() {

  private val viewModel by viewModels<AnalysisViewModel>()
  private val adapter by lazy { AnalysisAdapter() }

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosTradingviewAnalysisBinding {
    return FragmentCryptosTradingviewAnalysisBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initRecycler()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModel.listings("BINANCE", "1m", 1, 50)
    }
  }

  private fun initRecycler() {
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
  }

  private fun initViewModel() {
    viewModel.listings("BINANCE", "1m", 1, 50)
    viewModel.analysisPaginate.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          binding.rvListings.visibility = View.GONE
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            adapter.setData(it.data.transform())
          }
          binding.rvListings.visibility = View.VISIBLE
          showLoading(false)
        }
        is ApiResource.Error -> {
          binding.rvListings.visibility = View.GONE
          showToast(response.message ?: "api error")
          showLoading(false)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}
