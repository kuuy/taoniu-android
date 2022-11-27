package com.kuuy.taoniu.ui.cryptos.holders.tradingview

import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.WidgesCryptosTradingviewTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class TabPagerHolder(
  private val binding: WidgesCryptosTradingviewTabPagerBinding,
  private val initRecycler: (RecyclerView) -> Unit,
) : BaseListViewHolder<Nothing?, WidgesCryptosTradingviewTabPagerBinding>(binding) {
  override fun onBind(model: Nothing?, position: Int) {
    initRecycler(binding.rvListings)
  }
}