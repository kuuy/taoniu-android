package com.kuuy.taoniu.ui.cryptos.fragments

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.models.CoinInfo
import com.kuuy.taoniu.data.cryptos.mappings.transform

import com.kuuy.taoniu.R
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.databinding.FragmentCryptosOrderHomeBinding
import com.kuuy.taoniu.ui.cryptos.adapters.BannerAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.CoinListAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.OrderListAdapter

@AndroidEntryPoint
class OrderHomeFragment : BaseFragment<FragmentCryptosOrderHomeBinding>() {

  private val viewModels by viewModels<OrderViewModel>()
  private val coinListAdapter by lazy { CoinListAdapter() }
  private val mAdapter by lazy { OrderListAdapter() }
  private var snackBar: Snackbar? = null

  override fun viewBinding(container: ViewGroup?)
      : FragmentCryptosOrderHomeBinding {
    return FragmentCryptosOrderHomeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initRecycler()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModels.getOrderListings()
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

    binding.rvOrderList.apply {
      adapter = mAdapter
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
    binding.banner.lifecycleRegistry(lifecycle)
    binding.banner.setCanShowIndicator(true)
    binding.banner.setData(
      listOf(
        R.drawable.ic_banner_1,
        R.drawable.ic_banner_2,
        R.drawable.ic_banner_3,
        R.drawable.ic_banner_4
      )
    )

    viewModels.getOrderListings()
    viewModels.orderListings.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          binding.rvOrderList.visibility = View.GONE
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            mAdapter.setData(it.transform().orders)
          }
          binding.rvOrderList.visibility = View.VISIBLE
          showLoading(false)
        }
        is ApiResource.Error -> {
          binding.rvOrderList.visibility = View.GONE
          showLoading(false)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  } 
}
