package com.kuuy.taoniu.ui.base

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

abstract class BaseTabPagerAdapter<T, VH : RecyclerView.ViewHolder>
  : BaseListAdapter<T, VH>() {

  var isCanLoop = false

  var pageClickListener: OnPageClickListener? = null

  interface OnPageClickListener {
    fun onPageClick(position: Int, v: View)
  }

  var activatePosition = MutableLiveData<Int>()
  var realPosition = { position: Int ->
    var itemCount = listings.size

    when {
      !isCanLoop -> position
      else -> (position + itemCount) % itemCount
    }
  }

  abstract fun onRealBind(holder: VH, position: Int)

  override fun onBind(holder: VH, position: Int) {
    onRealBind(holder, realPosition(position))
  }

  fun getRealPosition(position: Int): Int {
    return realPosition(position)
  }

  abstract fun initDatas(size: Int)

  abstract fun activate(position: Int)
}