package com.kuuy.taoniu.ui.cryptos.holders

import com.kuuy.taoniu.data.cryptos.models.OrderInfo
import com.kuuy.taoniu.databinding.ItemCryptosOrderInfoBinding

import com.kuuy.taoniu.ui.base.BaseListViewHolder

class OrderInfoHolder(
  private val binding: ItemCryptosOrderInfoBinding
) : BaseListViewHolder<OrderInfo, ItemCryptosOrderInfoBinding>(binding) {

  override fun onBind(model: OrderInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    var positionText = ""
    when(model.positionSide) {
      "LONG" -> {
        positionText += "多头"
      }
      "SHORT" -> {
        positionText += "空头"
      }
    }
    when(model.side) {
      "BUY" -> {
        positionText += "加仓"
      }
      "SELL" -> {
        positionText += "减仓"
      }
    }
    binding.tvPosition.text = positionText
    binding.tvPrice.text = model.price.toString()
    binding.tvStatus.text = model.status
  }
}

