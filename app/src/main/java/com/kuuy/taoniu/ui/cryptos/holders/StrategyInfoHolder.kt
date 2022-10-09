package com.kuuy.taoniu.ui.cryptos.holders

import com.kuuy.taoniu.data.cryptos.models.StrategyInfo
import com.kuuy.taoniu.databinding.ItemCryptosStrategyInfoBinding

import com.kuuy.taoniu.ui.base.BaseListViewHolder

class StrategyInfoHolder(
  private val binding: ItemCryptosStrategyInfoBinding
) : BaseListViewHolder<StrategyInfo, ItemCryptosStrategyInfoBinding>(binding) {

  override fun onBind(model: StrategyInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    var positionText = ""
    when(model.signal) {
      1 -> {
        positionText += "看多"
      }
      2 -> {
        positionText += "看空"
      }
    }
    binding.tvPosition.text = positionText
    binding.tvPrice.text = model.price.toString()
    binding.tvCreatedAt.text = model.createdAt
  }
}

