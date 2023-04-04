package com.kuuy.taoniu.ui.cryptos.fragments

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.kuuy.taoniu.R
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

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosStrategyHomeBinding {
    return FragmentCryptosStrategyHomeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initSearchView()
    initRecycler()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModel.getStrategyListings()
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

  private fun initRecycler() {
    binding.rvListings.apply {
      adapter = this@StrategyHomeFragment.adapter
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
    viewModel.getStrategyListings()
    viewModel.strategyListings.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          binding.rvListings.visibility = View.GONE
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            adapter.addDatas(it.transform().data)
          }
          binding.rvListings.visibility = View.VISIBLE
          showLoading(false)
        }
        is ApiResource.Error -> {
          binding.rvListings.visibility = View.GONE
          showLoading(false)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}
