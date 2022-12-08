package com.kuuy.taoniu.ui.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.isolated.tradings.GridInfo

abstract class BaseListAdapter<T, VH: RecyclerView.ViewHolder>
    : RecyclerView.Adapter<VH>() {

  protected var listings = mutableListOf<T>()

  abstract fun viewHolder(parent: ViewGroup): VH

  abstract fun onBind(holder: VH, position: Int)

  override fun getItemCount(): Int = listings.size

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): VH {
    return viewHolder(parent)
  }

  override fun onBindViewHolder(
    holder: VH,
    position: Int
  ) {
    onBind(holder, position)
  }

  fun clear() {
    listings.clear()
  }

  @SuppressLint("NotifyDataSetChanged")
  fun addDatas(datas: List<T>) {
    listings.addAll(datas)
    notifyDataSetChanged()
  }
}

