package com.kuuy.taoniu.ui.article

import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kuuy.taoniu.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint

import com.kuuy.taoniu.ui.base.BaseFragment

@AndroidEntryPoint
class ArticleFragment : BaseFragment<FragmentArticleBinding>() {

  private val args by navArgs<ArticleFragmentArgs>()

  override fun viewBinding(
    container: ViewGroup?
  ): FragmentArticleBinding {
    return FragmentArticleBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    setupView()
  }

  private fun setupView() {
    binding.apply {
      wvArticle.webViewClient = object : WebViewClient() {}
      wvArticle.loadUrl(args.webParcel.url)
      wvArticle.settings.javaScriptEnabled = true
      wvArticle.settings.domStorageEnabled = true
      wvArticle.clearHistory()
      wvArticle.clearCache(true)
      wvArticle.clearSslPreferences()
      WebStorage.getInstance().deleteAllData()
      CookieManager.getInstance().removeAllCookies(null)
      CookieManager.getInstance().flush()
    }
  }
}
