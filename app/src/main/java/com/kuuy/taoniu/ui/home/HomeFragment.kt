package com.kuuy.taoniu.ui.home

import android.content.SharedPreferences
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.databinding.FragmentHomeBinding
import com.kuuy.taoniu.di.PreferencesModule
import com.kuuy.taoniu.ui.adapter.ItemNewsAdapter
import dagger.hilt.android.AndroidEntryPoint

import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.utils.*
import javax.inject.Named

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  private val viewModels by viewModels<HomeViewModel>()
  private val mAdapter by lazy { ItemNewsAdapter() }
  private var snackBar: Snackbar? = null

  protected override fun viewBinding(container: ViewGroup?): FragmentHomeBinding {
    return FragmentHomeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initRecycler()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModels.getNewsResponse()
    }
  }

  private fun initRecycler() {
    binding.rvNewsList.apply {
      adapter = mAdapter
      layoutManager = LinearLayoutManager(requireContext())
      setHasFixedSize(true)
    }
  }

  private fun initViewModel() {
    viewModels.getNewsResponse()
    viewModels.newsResponses.observe(
      viewLifecycleOwner
    ) { newsResponse ->
      when (newsResponse) {
        is ApiResource.Loading -> {
          binding.shimmerNewsList.visibility = View.VISIBLE
          binding.rvNewsList.visibility = View.GONE
          binding.ivErrorHome.visibility = View.GONE
          binding.tvErrorHome.visibility = View.GONE
          showLoading(true)
        }
        is ApiResource.Success -> {
          newsResponse.data?.let { mAdapter.setData(it) }
          binding.shimmerNewsList.visibility = View.GONE
          binding.rvNewsList.visibility = View.VISIBLE
          binding.ivErrorHome.visibility = View.GONE
          binding.tvErrorHome.visibility = View.GONE
          showLoading(false)
        }
        is ApiResource.Error -> {
          binding.shimmerNewsList.visibility = View.GONE
          binding.rvNewsList.visibility = View.GONE
          binding.ivErrorHome.visibility = View.VISIBLE
          binding.tvErrorHome.visibility = View.VISIBLE
          showErrorSnackBar()
          showLoading(false)
        }
      }
    }
  }

  private fun showErrorSnackBar() {
    snackBar = Snackbar.make(
      requireView(),
      "Error When Loading Data",
      Snackbar.LENGTH_INDEFINITE
    ).setAction("Retry") {
      viewModels.retryFailed()
    }
    snackBar?.show()
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}
