package com.kuuy.taoniu.ui.groceries.fragments

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.cryptos.models.CoinInfo
import com.kuuy.taoniu.data.groceries.mappings.transform

import com.kuuy.taoniu.R
import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.databinding.FragmentGroceriesProductsBinding
import com.kuuy.taoniu.ui.cryptos.adapters.BannerAdapter
import com.kuuy.taoniu.ui.cryptos.adapters.CoinListAdapter
import com.kuuy.taoniu.ui.groceries.adapters.ProductListAdapter
import com.kuuy.taoniu.ui.groceries.holders.ProductInfoHolder
import com.kuuy.taoniu.utils.DensityUtil
import com.kuuy.taoniu.utils.OnScrollListener
import com.kuuy.taoniu.utils.showToast
import timber.log.Timber

@AndroidEntryPoint
class ProductsFragment
    : BaseFragment<FragmentGroceriesProductsBinding>() {

  private val viewModel by viewModels<ProductViewModel>()
  private val coinListAdapter by lazy { CoinListAdapter() }
  private val productListAdapter by lazy {
    ProductListAdapter { model ->
      val action = ProductsFragmentDirections
          .toProductDetailFragment(model.remoteId)
      findNavController().navigate(action)
    }
  }
  private var snackBar: Snackbar? = null
  private var isLoading = false
  private var current = 1
  private val pageSize = 20

  override fun viewBinding(container: ViewGroup?)
      : FragmentGroceriesProductsBinding {
    return FragmentGroceriesProductsBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    initRecyclerView()
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
    }
  }

  private fun initRecyclerView() {
    binding.rvCoinList.apply {
      adapter = coinListAdapter
      layoutManager = LinearLayoutManager(
        requireContext(),
        LinearLayoutManager.HORIZONTAL,
        false
      )
    }

    binding.rvProductList.apply {
      adapter = productListAdapter
      layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
      addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
          if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            adapter?.notifyDataSetChanged()
          }
        }
      })
      setHasFixedSize(true)
    }
    val onScrollListener = OnScrollListener(binding.rvProductList.layoutManager as StaggeredGridLayoutManager) {
      viewModel.listings(current+1, pageSize)
    }
    binding.rvProductList.addOnScrollListener(onScrollListener)
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

    viewModel.listings(current, pageSize)
    viewModel.productPaginate.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          showLoading(false)
          response.data?.let {
            val products = it.data.transform()
            if (it.current == 1) {
              productListAdapter.clear()
            }
            if (products.isEmpty()) {
              return@observe
            }
            productListAdapter.addDatas(products)
            current = it.current
          }
        }
        is ApiResource.Error -> {
          showToast(response.apiError?.message ?: "api error")
          showLoading(false)
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    this.isLoading = isLoading
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }

  companion object {
    private val TAG = "PRODUCTS_FRAGMENT"
  }
}
