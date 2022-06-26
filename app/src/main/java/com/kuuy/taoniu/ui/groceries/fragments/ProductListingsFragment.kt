package com.kuuy.taoniu.ui.groceries.fragments

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


import com.kuuy.taoniu.utils.*
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.models.CoinInfo
import com.kuuy.taoniu.data.groceries.mappings.transform

import com.kuuy.taoniu.R
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.databinding.FragmentGroceriesProductListingsBinding
import com.kuuy.taoniu.ui.cryptos.adapters.BannerAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.CoinListAdapter
import com.kuuy.taoniu.ui.groceries.adapters.ProductListAdapter

@AndroidEntryPoint
class ProductListingsFragment
    : BaseFragment<FragmentGroceriesProductListingsBinding>() {

  private val viewModel by viewModels<ProductViewModel>()
  private val coinListAdapter by lazy { CoinListAdapter() }
  private val adapter by lazy {
    ProductListAdapter { model ->
      val action = ProductListingsFragmentDirections
          .toProductDetailFragment(model.remoteId)
      findNavController().navigate(action)
    }
  }
  private var snackBar: Snackbar? = null

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentGroceriesProductListingsBinding {
    return FragmentGroceriesProductListingsBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initRecycler()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModel.getProductListings()
    }
  }

  private fun initRecycler() {
    binding.rvCoinList.apply {
      adapter = coinListAdapter
      layoutManager = LinearLayoutManager(
        requireContext(),
        LinearLayoutManager.HORIZONTAL,
        false
      )
    }

    binding.rvProductList.apply {
      adapter = this@ProductListingsFragment.adapter
      layoutManager = LinearLayoutManager(requireContext())
      //setHasFixedSize(true)
    }
  }

  private fun initViewModel() {
    var coins: MutableList<CoinInfo> = mutableListOf()
    coins.add(CoinInfo(
      "BTC",
      "btc.svg"
    ))
    coins.add(CoinInfo(
      "ETH",
      "eth.svg"
    ))
    coins.add(CoinInfo(
      "AVAX",
      "avax.svg"
    ))
    coins.add(CoinInfo(
      "XRP",
      "xrp.svg"
    ))
    coins.add(CoinInfo(
      "ADA",
      "ada.svg"
    ))
    coinListAdapter.setData(coins)
    binding.rvCoinList.visibility = View.VISIBLE

    binding.banner.adapter = BannerAdapter()
    binding.banner.isAutoPlay = true
    binding.banner.isCanLoop = true
    binding.banner.setLifecycleRegistry(lifecycle)
    binding.banner.setCanShowIndicator(true)
    binding.banner.setData(
      listOf(
        R.drawable.ic_banner_1,
        R.drawable.ic_banner_2,
        R.drawable.ic_banner_3,
        R.drawable.ic_banner_4
      )
    )

    viewModel.getProductListings()
    viewModel.productListings.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
          binding.rvProductList.visibility = View.GONE
        }
        is ApiResource.Success -> {
          showLoading(false)
          response.data?.let {
            adapter.setData(it.transform().products)
          }
          binding.rvProductList.visibility = View.VISIBLE
        }
        is ApiResource.Error -> {
          showLoading(false)
          binding.rvProductList.visibility = View.GONE
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  } 
}
