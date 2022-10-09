package com.kuuy.taoniu.ui.groceries.fragments

import android.app.Activity
import android.Manifest
import android.content.Intent
import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import com.kuuy.taoniu.R
import com.kuuy.taoniu.utils.*
import com.kuuy.taoniu.data.ApiResource
import com.kuuy.taoniu.data.DbResource
import com.kuuy.taoniu.data.images.mappings.transform
import com.kuuy.taoniu.data.groceries.mappings.transform
import com.kuuy.taoniu.data.groceries.models.ProductBarcode
import com.kuuy.taoniu.data.groceries.models.ProductDetail

import com.kuuy.taoniu.ui.base.BaseFragment
import com.kuuy.taoniu.ui.images.fragments.ImageViewModel
import com.kuuy.taoniu.databinding.FragmentGroceriesProductDetailBinding

@AndroidEntryPoint
class ProductDetailFragment
    : BaseFragment<FragmentGroceriesProductDetailBinding>() {

  enum class ImagePickUpOptions(val resID:Int) {
    CAMERA(R.string.camera_text),
    ALBUM(R.string.gallery_text)
  }

  private lateinit var imageUri: Uri
  private val args by navArgs<ProductDetailFragmentArgs>()
  private val viewModel by viewModels<ProductViewModel>()
  private val imageViewModel by viewModels<ImageViewModel>()
  private var snackBar: Snackbar? = null
  private lateinit var productDetail: ProductDetail

  private val resultLauncherCamera =
      registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
      ) { result ->
        if (::imageUri.isInitialized &&
            result.resultCode == Activity.RESULT_OK) {
          imageViewModel.upload(
            imageUri,
            requireActivity().contentResolver
          )
        }
      }

  private val resultLauncherGallery =
      registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
      ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
          imageUri = result.data?.data!!
          imageViewModel.upload(
            imageUri,
            requireActivity().contentResolver
          )
        }
      }

  private fun requestCamaraPermission() {
    if (
      ActivityCompat.shouldShowRequestPermissionRationale(
        requireActivity(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
      )
      &&
      ActivityCompat.shouldShowRequestPermissionRationale(
        requireActivity(),
        Manifest.permission.CAMERA
      )
    ) {
      showToast("need camara permission")
    } else {
      requestPermission.launch(arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
      ))
    }
  }

  private fun requestStoragePermission() {
    if (
      ActivityCompat.shouldShowRequestPermissionRationale(
        requireActivity(),
        Manifest.permission.READ_EXTERNAL_STORAGE
      )
    ) {
      showToast("need storage read permission")
    } else {
      requestPermission.launch(arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
      ))
    }
  }  

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentGroceriesProductDetailBinding {
    return FragmentGroceriesProductDetailBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    initViewModel()
    viewModel.getProductDetail(args.id)
    binding.swipeRefreshLayout.setOnRefreshListener {
    }
    viewModel.productUpdate.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          showLoading(false)
          response.data?.let{
            val action = ProductDetailFragmentDirections
                .toProductListingsFragment()
            findNavController().navigate(action)
          }
        }
        is ApiResource.Error -> {
          showLoading(false)
          showToast(response.message ?: "api failed")
        }
      }
    }

    binding.btnUpload.setOnClickListener {
      showOptionsDialog(
        ImagePickUpOptions.values().map {
          getString(it.resID)
        }.toTypedArray(),
        getString(R.string.image_pick_up)
      ) { option ->
        when (option) {
          ImagePickUpOptions.CAMERA.ordinal -> requestCamaraPermission()
          ImagePickUpOptions.ALBUM.ordinal -> requestStoragePermission()
        }
      }
    }

    binding.btnAdd.setOnClickListener {
      if (::productDetail.isInitialized) {
        viewModel.addToCounter(
          productDetail.remoteId,
          productDetail.title,
          productDetail.price
        )
      }
    }

    binding.btnSubmit.setOnClickListener {
      viewModel.updateProduct(
        args.id,
        binding.tvBarcode.text.toString(),
        binding.etTitle.text.toString(),
        binding.etIntro.text.toString(),
        binding.etPrice.text.toString().toFloat(),
        binding.tvCover.text.toString(),
      )
    }
  }

  private fun initViewModel() {
    viewModel.productDetail.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          showLoading(false)
          response.data?.let {
            productDetail = it.transform()
            binding.tvBarcode.text = productDetail.barcode
            binding.etTitle.setText(productDetail.title)
            binding.etIntro.setText(productDetail.intro)
            binding.etPrice.setText(productDetail.price.toString())
            binding.tvCover.text = productDetail.cover
          }
        }
        is ApiResource.Error -> {
          showLoading(false)
          if (response.code == 404) {
            val action = ProductDetailFragmentDirections
                .toProductListingsFragment()
            findNavController().navigate(action)
          } else {
            showToast(response.message ?: "api failed")
          }
        }
      }
    }

    imageViewModel.imageInfo.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          showLoading(false)
          response.data?.let {
            binding.tvCover.text = it.transform().filename
          }
        }
        is ApiResource.Error -> {
          showToast(response.message ?: "api error")
          showLoading(false)
        }
      }
    }

    viewModel.productUpdate.observe(
      viewLifecycleOwner
    ) { response->
      when (response) {
        is ApiResource.Loading -> {
          showLoading(true)
        }
        is ApiResource.Success -> {
          showLoading(false)
          val action = ProductDetailFragmentDirections
              .toProductListingsFragment()
          findNavController().navigate(action)
        }
        is ApiResource.Error -> {
          showLoading(false)
          showToast(response.message ?: "api failed")
        }
      }
    }

    viewModel.productAddToCounter.observe(
      viewLifecycleOwner
    ) { result->
      when (result) {
        is DbResource.Loading -> {
          showLoading(true)
        } 
        is DbResource.Success -> {
          showLoading(false)
          showToast("add to counter success")
        }
        is DbResource.Error -> {
          showLoading(false)
          showToast(result.message ?: "db failed")
        }
      }
    }
  }

  override protected fun bindCameraUseCases() {
    imageUri = requireActivity().contentResolver.insert(
      MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues()
    )!!
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
    resultLauncherCamera.launch(intent)
  }

  override protected fun bindStorageUseCases() {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    resultLauncherGallery.launch(intent)
  }

  private fun showLoading(isLoading: Boolean) {
    if (isLoading) {
      binding.tvCover.text = "uploading ..."
    } else {
      binding.tvCover.text = ""
    }
  }
}
