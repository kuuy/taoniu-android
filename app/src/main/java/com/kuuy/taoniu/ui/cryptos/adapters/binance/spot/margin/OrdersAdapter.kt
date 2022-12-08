package com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.OrderInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotMarginOrdersBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.binance.spot.margin.OrdersHolder

class OrdersAdapter(
  private val onItemClicked: (OrderInfo) -> Unit,
): BaseListAdapter<OrderInfo, OrdersHolder>() {
  override fun viewHolder(parent: ViewGroup): OrdersHolder {
    var binding = ItemCryptosBinanceSpotMarginOrdersBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return OrdersHolder(binding)
  }

  override fun onBind(holder: OrdersHolder, position: Int) {
    val model = listings[position]
    holder.onBind(model, position)
    holder.itemView.setOnClickListener {
      onItemClicked(model)
    }
  }
}