package com.kuuy.taoniu.ui.cryptos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import com.kuuy.taoniu.utils.DiffUtils
import com.kuuy.taoniu.data.cryptos.models.OrderInfo
import com.kuuy.taoniu.databinding.ItemCryptosOrderInfoBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.OrderInfoHolder

class OrderListAdapter
    : BaseListAdapter<OrderInfo, OrderInfoHolder>() {

  override fun viewHolder(parent: ViewGroup): OrderInfoHolder {
    var binding = ItemCryptosOrderInfoBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return OrderInfoHolder(binding)
  }

  override fun onBind(holder: OrderInfoHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  fun setData(orders: List<OrderInfo>) {
    val diffUtil = DiffUtils<OrderInfo>(listings, orders)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    listings = orders as MutableList<OrderInfo>
    diffUtilResult.dispatchUpdatesTo(this)
  }

}

