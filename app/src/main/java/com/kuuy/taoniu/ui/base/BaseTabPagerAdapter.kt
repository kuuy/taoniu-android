package com.kuuy.taoniu.ui.base

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

abstract class BaseTabPagerAdapter<VH : RecyclerView.ViewHolder>
  : BaseListAdapter<Boolean, VH>() {
  var position = MutableLiveData<Int>()

  @SuppressLint("NotifyDataSetChanged")
  fun size(size: Int, lifecycle: LifecycleOwner) {
    listings.clear()
    (0 until size).map {
      listings.add(false)
    }
    notifyDataSetChanged()
    position.observe(lifecycle) {
      for (i in 0 until listings.size) {
        if (listings[i]) {
          listings[i] = false
          notifyItemChanged(i)
        }
      }
      listings[it] = true
      notifyItemChanged(it)
    }
  }
}