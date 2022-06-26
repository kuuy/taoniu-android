package com.kuuy.taoniu.ui.main.fragments

import dagger.hilt.android.AndroidEntryPoint

import android.view.ViewGroup
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ExecutionException
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import kotlin.IllegalStateException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

import com.kuuy.taoniu.databinding.FragmentScannerBinding
import com.kuuy.taoniu.utils.showToast
import com.kuuy.taoniu.ui.base.BaseFragment


@AndroidEntryPoint
class ScannerFragment: BaseFragment<FragmentScannerBinding>() {
  lateinit private var cameraProvider: ProcessCameraProvider
  lateinit private var cameraSelector: CameraSelector
  lateinit private var previewUseCase: Preview
  lateinit private var analysisUseCase: ImageAnalysis

  private var cameraProviderLiveData
      = MutableLiveData<ProcessCameraProvider>()

  private val screenAspectRatio: Int
      get() {
        return aspectRatio(
          binding.previewView.width,
          binding.previewView.height
        )
      }

  protected override fun viewBinding(container: ViewGroup?)
      : FragmentScannerBinding {
    return FragmentScannerBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    cameraSelector = CameraSelector.Builder().requireLensFacing(
      CameraSelector.LENS_FACING_BACK
    ).build()
   
    val cameraProviderFuture =
        ProcessCameraProvider.getInstance(requireContext())

    cameraProviderFuture.addListener({
      try {
        cameraProviderLiveData.setValue(
          cameraProviderFuture.get()
        )
      } catch (executionException: ExecutionException) {
        showToast(
          executionException.message ?: "executionException"
        )
      } catch (interruptedException: InterruptedException) {
        showToast(
          interruptedException.message ?: "interruptedException"
        )
      }
    }, ContextCompat.getMainExecutor(requireContext()))

    cameraProviderLiveData.observe(
      requireActivity()
    ) { provider: ProcessCameraProvider ->
      cameraProvider = provider
      requestCamaraPermission()
    }
  }

  override protected fun bindCameraUseCases() {
    bindPreviewUseCase()
    bindAnalyseUseCase()
  }

  private fun bindPreviewUseCase() {
    if (!::cameraProvider.isInitialized) {
      return
    }
    if (::previewUseCase.isInitialized) {
      cameraProvider.unbind(previewUseCase)
    }

    previewUseCase = Preview.Builder()
        .setTargetAspectRatio(screenAspectRatio)
        .setTargetRotation(binding.previewView.display.rotation)
        .build()

    previewUseCase.setSurfaceProvider(
      binding.previewView.surfaceProvider
    )

    try {
      cameraProvider.bindToLifecycle(
        this,
        cameraSelector,
        previewUseCase
      )
    } catch (illegalStateException: IllegalStateException) {
      showToast(
        illegalStateException.message ?: "IllegalStateException"
      )
    } catch (illegalArgumentException: IllegalArgumentException) {
      showToast(
        illegalArgumentException.message ?: "IllegalArgumentException"
      )
    }
  }

  private fun bindAnalyseUseCase() {
    BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()

    if (!::cameraProvider.isInitialized) {
      return
    }

    if (::analysisUseCase.isInitialized) {
      cameraProvider.unbind(analysisUseCase)
    }

    analysisUseCase = ImageAnalysis.Builder()
        .setTargetAspectRatio(screenAspectRatio)
        .setTargetRotation(binding.previewView.display.rotation)
        .build()

    val cameraExecutor = Executors.newSingleThreadExecutor()

    analysisUseCase.setAnalyzer(
      cameraExecutor,
      ImageAnalysis.Analyzer { imageProxy ->
        processImageProxy(barcodeScanner, imageProxy)
      }
    )

    try {
      cameraProvider.bindToLifecycle(
        this,
        cameraSelector,
        analysisUseCase
      )
    } catch (illegalStateException: IllegalStateException) {
      showToast(
        illegalStateException.message ?: "IllegalStateException"
      )
    } catch (illegalArgumentException: IllegalArgumentException) {
      showToast(
        illegalArgumentException.message ?: "IllegalArgumentException"
      )
    }
  }

  private fun processImageProxy(
    barcodeScanner: BarcodeScanner,
    imageProxy: ImageProxy
  ) {
    val inputImage = InputImage.fromMediaImage(
      imageProxy.image!!,
      imageProxy.imageInfo.rotationDegrees
    )

    barcodeScanner.process(inputImage)
        .addOnSuccessListener { codes ->
          var barcode: String? = null
          for (code in codes) {
            val valueType = code.valueType
            when (valueType) {
              Barcode.TYPE_TEXT -> {
                showToast("TEXT")
              }
              Barcode.TYPE_PRODUCT -> {
                barcode = code.rawValue
                break
              }
              else -> {
                showToast(
                  "type:%d value:%s".format(
                    valueType,
                    code.rawValue,
                  )
                )
              }
            }
          }

          if (barcode != null) {
            processBarcode(barcode)
          }
        }
        .addOnFailureListener {
          showToast(it.message ?: it.toString())
        }.addOnCompleteListener {
          imageProxy.close()
        }
  }

  private fun processBarcode(barcode: String) {
    analysisUseCase.clearAnalyzer()
    cameraProvider.unbind(analysisUseCase)

    val action = ScannerFragmentDirections
      .toProductBarcodeFragment(barcode)
    findNavController().navigate(action)
  }

  private fun aspectRatio(width: Int, height: Int): Int {
    val previewRatio = max(width, height).toDouble() / min(width, height)
    if (abs(previewRatio - RATIO_4_3_VALUE)
        <= abs(previewRatio - RATIO_16_9_VALUE)) {
      return AspectRatio.RATIO_4_3
    }
    return AspectRatio.RATIO_16_9
  }

  private fun requestCamaraPermission() {
    if (
      ActivityCompat.shouldShowRequestPermissionRationale(
        requireActivity(),
        Manifest.permission.CAMERA
      )
    ) {
      showToast("need camara permission")
    } else {
      requestPermission.launch(arrayOf(
        Manifest.permission.CAMERA
      ))
    }
  }

  companion object {
    private const val PERMISSION_CAMERA_REQUEST = 1

    private const val RATIO_4_3_VALUE = 4.0 / 3.0
    private const val RATIO_16_9_VALUE = 16.0 / 9.0
  }
}
