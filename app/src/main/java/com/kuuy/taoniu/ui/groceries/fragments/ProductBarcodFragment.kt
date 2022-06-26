package com.kuuy.taoniu.ui.groceries.fragments

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import com.kuuy.taoniu.utils.*
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.groceries.mappings.transform
import com.kuuy.taoniu.data.groceries.models.ProductBarcode

import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.databinding.FragmentGroceriesProductBarcodeBinding

@AndroidEntryPoint
class ProductBarcodeFragment
    : BaseFragment<FragmentGroceriesProductBarcodeBinding>() {

  private val args by navArgs<ProductBarcodeFragmentArgs>()
  private val viewModels by viewModels<ProductViewModel>()
  private var snackBar: Snackbar? = null

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentGroceriesProductBarcodeBinding {
    return FragmentGroceriesProductBarcodeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModels.getProductBarcode(args.barcode)
    }
  }

  private fun initViewModel() {
    viewModels.getProductBarcode(args.barcode)
    viewModels.productBarcode.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            showLoading(false)
            val action = ProductBarcodeFragmentDirections
                .toProductDetailFragment(it.transform().productId)
            findNavController().navigate(action)
          }
        }
        is ApiResource.Error -> {
          showLoading(false)
          if (response.code == 404) {
            val action = ProductBarcodeFragmentDirections
                .toProductCreateFragment(args.barcode)
            findNavController().navigate(action)
          } else {
            showToast(response.message ?: "api failed")
          }
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}
