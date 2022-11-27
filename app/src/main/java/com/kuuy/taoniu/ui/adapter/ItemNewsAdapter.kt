package com.kuuy.taoniu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kuuy.taoniu.data.model.ArticlesItem
import com.kuuy.taoniu.data.model.NewsResponse
import com.kuuy.taoniu.databinding.ItemNewsListBinding
import com.kuuy.taoniu.utils.DiffUtils

import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.holder.NewsViewHolder

class ItemNewsAdapter : BaseListAdapter<ArticlesItem, NewsViewHolder>() {

  override fun viewHolder(parent: ViewGroup)
      : NewsViewHolder {
    var binding = ItemNewsListBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return NewsViewHolder(binding)
  }

  override fun onBind(holder: NewsViewHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  fun setData(newData: NewsResponse) {
    val newsDiffUtil = DiffUtils(listings, newData.articles)
    val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
    listings = newData.articles as MutableList<ArticlesItem>
    diffUtilResult.dispatchUpdatesTo(this)
  }

}

