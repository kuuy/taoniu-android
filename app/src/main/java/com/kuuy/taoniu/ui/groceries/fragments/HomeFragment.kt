package com.kuuy.taoniu.ui.groceries.fragments

import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuuy.taoniu.R
import com.kuuy.taoniu.databinding.FragmentCryptosBinanceSpotPlansDailyBinding
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.groceries.adapters.FeedListAdapter
import com.kuuy.taoniu.utils.OnScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentCryptosBinanceSpotPlansDailyBinding>() {
  private val viewModel by viewModels<HomeViewModel>()
  private val adapter by lazy { FeedListAdapter{ model ->
//    val action = HomeFragmentDirections
//      .toTrade(model.symbol)
//    findNavController().navigate(action)
  } }
  private var isLoading = false
  private var current = 1
  private val pageSize = 20

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosBinanceSpotPlansDailyBinding {
    return FragmentCryptosBinanceSpotPlansDailyBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initSearchView()
    initRecycler()
    initViewModel()
  }

  private fun initSearchView() {
    binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }

      override fun onQueryTextChange(newText: String?): Boolean {
//        adapter.filter.filter(newText)
        return false
      }
    })
  }

  private fun initRecycler() {
    viewModel.getFeedListings(current, pageSize)
    binding.rvListings.apply {
      adapter = this@HomeFragment.adapter
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
      viewModel.getFeedListings(current+1, pageSize)
    }
    binding.rvListings.addOnScrollListener(onScrollListener)
  }

  private fun initViewModel() {
//    viewModel.feedsPaginate.observe(
//      viewLifecycleOwner
//    ) { response->
//      when (response) {
//        is ApiResource.Loading -> {
//          showLoading(true)
//        }
//        is ApiResource.Success -> {
//          response.data?.let {
//            val plans = it.data.transform()
//            adapter.addDatas(plans)
//            current = it.current
//          }
//          showLoading(false)
//        }
//        is ApiResource.Error -> {
//          showToast(response.apiError?.message ?: "api error")
//          showLoading(false)
//        }
//      }
//    }
  }

  private fun showLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}