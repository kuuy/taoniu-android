package com.kuuy.taoniu.ui.cryptos.holders.tradingview

import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.ItemCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class AnalysisHolder(
  private val binding: ItemCryptosTradingviewAnalysisBinding
) : BaseListViewHolder<AnalysisInfo, ItemCryptosTradingviewAnalysisBinding>(binding) {

  override fun onBind(model: AnalysisInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    binding.tvRecommendation.text = model.summary.recommendation
  }
}