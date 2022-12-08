package com.kuuy.taoniu.ui.cryptos.holders.tradingview

import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.databinding.WidgesCryptosTradingviewTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class TabPagerHolder(
  private val binding: WidgesCryptosTradingviewTabPagerBinding,
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
) : BaseListViewHolder<Boolean, WidgesCryptosTradingviewTabPagerBinding>(binding) {
  override fun onBind(model: Boolean, position: Int) {
    binding.rvListings.adapter = null
    binding.rvListings.clearOnScrollListeners()
    if (model) {
      initRecyclerView(binding.rvListings, position)
    }
  }
}