package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.margin.isolated.tradings

import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.isolated.tradings.GridInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotMarginIsolatedTradingsGridsBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class GridsHolder(
  private val binding: ItemCryptosBinanceSpotMarginIsolatedTradingsGridsBinding,
) : BaseListViewHolder<GridInfo, ItemCryptosBinanceSpotMarginIsolatedTradingsGridsBinding>(binding) {
  override fun onBind(model: GridInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    binding.tvPrice.text = model.buyPrice.toString()
    if (model.status == 0) {
      binding.tvStatus.text = "待买入"
    } else if (model.status == 1) {
      binding.tvStatus.text = "已买入"
    } else if (model.status == 2) {
      binding.tvStatus.text = "待出售"
    } else if (model.status == 3) {
      binding.tvStatus.text = "已完成"
    }
    binding.tvTimestamp.text = model.timestampFormat
  }
}