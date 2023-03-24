package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.analysis.tradings.fishers

import com.kuuy.taoniu.data.cryptos.models.binance.spot.analysis.tradings.fishers.GridInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotAnalysisTradingsFishersGridsBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class GridsHolder(
  private val binding: ItemCryptosBinanceSpotAnalysisTradingsFishersGridsBinding,
) : BaseListViewHolder<GridInfo, ItemCryptosBinanceSpotAnalysisTradingsFishersGridsBinding>(binding) {
  override fun onBind(model: GridInfo, position: Int) {
    binding.tvDay.text = model.day
    binding.lcBuysCount.value = model.buysCount
    binding.lcBuysAmount.value = model.buysAmount
    binding.lcSellsCount.value = model.sellsCount
    binding.lcSellsAmount.value = model.sellsAmount
  }
}