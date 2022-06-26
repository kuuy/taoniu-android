package com.kuuy.taoniu.ui.cryptos.fragments

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


import com.kuuy.taoniu.utils.*
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.mappings.transform
import com.kuuy.taoniu.databinding.FragmentCryptosStrategyHomeBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.cryptos.adapters.StrategyListAdapter

@AndroidEntryPoint
class StrategyHomeFragment : BaseFragment<FragmentCryptosStrategyHomeBinding>() {

  private val viewModel by viewModels<StrategyViewModel>()
  private val adapter by lazy { StrategyListAdapter() }
  private var snackBar: Snackbar? = null

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosStrategyHomeBinding {
    return FragmentCryptosStrategyHomeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initRecycler()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModel.getStrategyListings()
    }
  }

  private fun initRecycler() {
    binding.rvStrategyList.apply {
      adapter = this@StrategyHomeFragment.adapter
      layoutManager = LinearLayoutManager(requireContext())
      setHasFixedSize(true)
    }
  }

  private fun initViewModel() {
    viewModel.getStrategyListings()
    viewModel.strategyListings.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          binding.rvStrategyList.visibility = View.GONE
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            adapter.setData(it.transform().strategies)
          }
          binding.rvStrategyList.visibility = View.VISIBLE
          showLoading(false)
        }
        is ApiResource.Error -> {
          binding.rvStrategyList.visibility = View.GONE
          showLoading(false)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  } 
}
