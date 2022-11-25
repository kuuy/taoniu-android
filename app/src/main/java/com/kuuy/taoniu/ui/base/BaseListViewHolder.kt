package com.kuuy.taoniu.ui.base

import androidx.viewbinding.ViewBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListViewHolder<T, B : ViewBinding>(
  private var binding : B
) : RecyclerView.ViewHolder(binding.root) {
  abstract fun onBind(model: T, position: Int)
}
