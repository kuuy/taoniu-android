package com.kuuy.taoniu.ui.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kuuy.taoniu.di.PreferencesModule
import javax.inject.Inject
import javax.inject.Named

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
  protected lateinit var binding: B

  @Inject
  @Named(PreferencesModule.AUTH_PREFERENCES) lateinit var authPreferences: SharedPreferences

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
