package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.tradings

import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.databinding.WidgesCryptosBinanceSpotTradingsSymbolsBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class TabPagerHolder(
  private val binding: WidgesCryptosBinanceSpotTradingsSymbolsBinding,
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
) : BaseListViewHolder<Boolean, WidgesCryptosBinanceSpotTradingsSymbolsBinding>(binding) {
  override fun onBind(model: Boolean, position: Int) {
    if (model) {
      initRecyclerView(binding.rvListings, position)
    }
  }
}