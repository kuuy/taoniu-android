package com.kuuy.taoniu.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
  protected lateinit var binding: B

  protected abstract fun viewBinding(): B

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = viewBinding()
    getSupportActionBar()?.hide()
    setContentView(binding.root)
    onBind()
  }

  protected abstract fun onBind()
}
