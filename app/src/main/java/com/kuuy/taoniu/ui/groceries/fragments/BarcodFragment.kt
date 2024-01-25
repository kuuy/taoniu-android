package com.kuuy.taoniu.ui.groceries.fragments

import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import com.kuuy.taoniu.utils.*
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.groceries.mappings.transform

import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.databinding.FragmentGroceriesBarcodeBinding

@AndroidEntryPoint
class BarcodeFragment
    : BaseFragment<FragmentGroceriesBarcodeBinding>() {

  private val args by navArgs<BarcodeFragmentArgs>()
  private val viewModels by viewModels<ProductViewModel>()
  private var snackBar: Snackbar? = null

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentGroceriesBarcodeBinding {
    return FragmentGroceriesBarcodeBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initViewModel()
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModels.get(args.barcode)
    }
  }

  private fun initViewModel() {
    viewModels.get(args.barcode)
    viewModels.barcodeInfo.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          response.data?.let {
            showLoading(false)
            val action = BarcodeFragmentDirections
                .toProductDetailFragment(it.transform().productId)
            findNavController().navigate(action)
          }
        }
        is ApiResource.Error -> {
          showLoading(false)
          if (response.apiError?.code == 404) {
            val action = BarcodeFragmentDirections
                .toProductCreateFragment(args.barcode)
            findNavController().navigate(action)
          } else {
            showToast(response.apiError?.message ?: "api failed")
          }
        }
      }
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.swipeRefreshLayout.isRefreshing = isLoading
  }
}
