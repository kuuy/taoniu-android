package com.kuuy.taoniu.ui.holder

import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.model.ArticlesItem
import com.kuuy.taoniu.data.model.NewsResponse
import com.kuuy.taoniu.databinding.ItemNewsListBinding
import com.kuuy.taoniu.ui.home.HomeFragmentDirections
import com.kuuy.taoniu.utils.DiffUtils
import org.jsoup.Jsoup

import com.kuuy.taoniu.ui.base.BaseListViewHolder

class NewsViewHolder(
  private val binding: ItemNewsListBinding
) : BaseListViewHolder<ArticlesItem, ItemNewsListBinding>(binding) {

  override fun onBind(model: ArticlesItem, position: Int) {
    binding.ivNewsImage.load(model.urlToImage) {
      crossfade(100)
      error(R.drawable.ic_no_image)
    }
    binding.tvNewsTitle.text = model.title
    if (model.description != null) {
      val desc = Jsoup.parse(model.description).text()
      binding.tvNewsDesc.text = desc
    } else {
      binding.tvNewsDesc.text = buildString {
        append("No Description.")
      }
    }
    binding.newsCardView.setOnClickListener {
      val action = HomeFragmentDirections
          .actionHomeFragmentToDetailFragment(model)
      binding.newsCardView.findNavController().navigate(action)
    }
  }
}

