package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.analysis.margin.isolated.tradings.fishers

import com.kuuy.taoniu.data.cryptos.models.binance.spot.analysis.margin.isolated.tradings.fishers.GridInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class GridsHolder(
  private val binding: ItemCryptosBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsBinding,
) : BaseListViewHolder<GridInfo, ItemCryptosBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsBinding>(binding) {
  override fun onBind(model: GridInfo, position: Int) {
    binding.tvDay.text = model.day
    binding.lcBuysCount.value = model.buysCount
    binding.lcBuysAmount.value = model.buysAmount
    binding.lcSellsCount.value = model.sellsCount
    binding.lcSellsAmount.value = model.sellsAmount
  }
}