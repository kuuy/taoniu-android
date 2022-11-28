package com.kuuy.taoniu.ui.cryptos.holders.tradingview

import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.WidgesCryptosTradingviewTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder
import com.kuuy.taoniu.ui.cryptos.fragments.tradingview.AnalysisFragment
import timber.log.Timber

class TabPagerHolder(
  private val binding: WidgesCryptosTradingviewTabPagerBinding,
  private val initRecycler: (RecyclerView, Int) -> Unit,
) : BaseListViewHolder<Boolean, WidgesCryptosTradingviewTabPagerBinding>(binding) {
  override fun onBind(model: Boolean, position: Int) {
    binding.rvListings.adapter = null
    binding.rvListings.clearOnScrollListeners()
    if (model) {
      initRecycler(binding.rvListings, position)
    }
  }
}