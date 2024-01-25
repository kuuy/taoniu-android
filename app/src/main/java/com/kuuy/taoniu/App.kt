package com.kuuy.taoniu

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

import coil.Coil
import coil.Coil.setImageLoader
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.CachePolicy

@HiltAndroidApp
class App : Application() {
  override fun onCreate() {
    super.onCreate()

    Coil.setImageLoader(
      ImageLoader.Builder(this@App)
        .components {
          add(SvgDecoder.Factory())
        }
        .build()
    )

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}
