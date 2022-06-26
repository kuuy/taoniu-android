package com.kuuy.taoniu.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.model.ArticlesItem
import com.kuuy.taoniu.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

import com.kuuy.taoniu.ui.base.BaseFragment

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

  private val args by navArgs<DetailFragmentArgs>()

  override fun viewBinding(
    container: ViewGroup?
  ): FragmentDetailBinding {
    return FragmentDetailBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  override fun onBind() {
    iniView()
  }

  private fun iniView() {
    binding.apply {
      val newsParcel: ArticlesItem = args.newsParcel

      tvNewsDetailTitle.text = newsParcel.title

      ivNewsDetail.load(newsParcel.urlToImage) {
        crossfade(200)
        error(R.drawable.ic_no_image)
      }

      if (newsParcel.author != null) {
        val auth = Jsoup.parse(newsParcel.author).text()
        tvNewsDetailAuthor.text = auth
      } else {
        tvNewsDetailAuthor.text = getString(R.string.no_author)
      }

      if (newsParcel.content != null) {
        val content = Jsoup.parse(newsParcel.content).text()
        tvNewsDetailDescription.text = content
      } else {
        tvNewsDetailDescription.text = getString(R.string.no_desc)
      }

      btnReadFullArticle.setOnClickListener {
        val action = DetailFragmentDirections.actionDetailFragmentToArticleFragment(args.newsParcel)
        findNavController().navigate(action)
      }

    }
  }

}
