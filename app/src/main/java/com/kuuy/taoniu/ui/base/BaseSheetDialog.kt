package com.kuuy.taoniu.ui.base

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.kuuy.taoniu.utils.showToast

abstract class BaseSheetDialog<B:ViewBinding>
    : BottomSheetDialogFragment() {
  private var _binding: B? = null
  protected val binding get() = _binding!!

  protected abstract fun viewBinding(container: ViewGroup?): B

  protected val requestPermission
      : ActivityResultLauncher<Array<String>> = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
      ) { permissions ->

    var grantedKeys = mutableListOf<String>()
    permissions.entries.forEach {
      val permissionName = it.key
      val isGranted = it.value

      if (isGranted) {
        grantedKeys.add(permissionName)
      } else {
        if (permissionName == Manifest.permission.CAMERA) {
          showToast(
            "you just denied the camara permission.",
            Toast.LENGTH_LONG
          )
        }
        if (permissionName == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
          showToast(
            "you just denied the storage write permission.",
            Toast.LENGTH_LONG
          )
        }
        if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
          showToast(
            "you just denied the storage read permission.",
            Toast.LENGTH_LONG
          )
        }
        grantedKeys.clear()
        return@forEach
      }
    }

    grantedKeys.forEach{ permissionName ->
      if (permissionName == Manifest.permission.CAMERA) {
        bindCameraUseCases()
      }
      if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
        bindStorageUseCases()
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = viewBinding(container)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onBind()
  }

  protected abstract fun onBind()

  protected open fun bindCameraUseCases() {}

  protected open fun bindStorageUseCases() {}

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
