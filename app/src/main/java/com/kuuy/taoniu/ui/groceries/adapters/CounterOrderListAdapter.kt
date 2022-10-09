package com.kuuy.taoniu.ui.groceries.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import com.kuuy.taoniu.utils.DiffUtils
import com.kuuy.taoniu.data.groceries.models.CounterOrderInfo
import com.kuuy.taoniu.databinding.ItemGroceriesCounterOrderInfoBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.groceries.holders.CounterOrderInfoHolder

class CounterOrderListAdapter(
  private val onItemClicked: (CounterOrderInfo) -> Unit,
  private val onItemRemoved: (CounterOrderInfo) -> Unit
) : BaseListAdapter<CounterOrderInfo, CounterOrderInfoHolder>() {

  override fun viewHolder(parent: ViewGroup): CounterOrderInfoHolder {
    var binding = ItemGroceriesCounterOrderInfoBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return CounterOrderInfoHolder(binding)
  }

  override fun onBind(holder: CounterOrderInfoHolder, position: Int) {
    val model = listings[position]
    holder.onBind(model, position)
    holder.itemView.setOnClickListener {
      onItemClicked(model)
    }
  }

  fun remove(position: Int) {
    val model = listings[position] 
    onItemRemoved(model)
    listings.drop(position)
  }

  fun setData(orders: List<CounterOrderInfo>) {
    val diffUtil = DiffUtils<CounterOrderInfo>(listings, orders)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    listings = orders
    diffUtilResult.dispatchUpdatesTo(this)
  }

}

