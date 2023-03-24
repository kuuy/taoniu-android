package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.analysis.tradings.fishers

import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.databinding.WidgesCryptosBinanceSpotAnalysisTradingsFishersGridsBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class TabPagerHolder(
  private val binding: WidgesCryptosBinanceSpotAnalysisTradingsFishersGridsBinding,
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
) : BaseListViewHolder<Boolean, WidgesCryptosBinanceSpotAnalysisTradingsFishersGridsBinding>(binding) {
  override fun onBind(model: Boolean, position: Int) {
    if (model) {
      initRecyclerView(binding.rvListings, position)
    }
  }
}