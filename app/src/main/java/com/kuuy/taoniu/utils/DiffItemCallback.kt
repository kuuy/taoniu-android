package com.kuuy.taoniu.utils

import androidx.recyclerview.widget.DiffUtil

class DiffItemCallback<T: Any>: DiffUtil.ItemCallback<T>() {
  override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
    return false
  }

  override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
    return false
  }
}

