package com.kuuy.taoniu.ui.cryptos.holders.tradingview

import android.content.Context
import android.view.animation.AnimationUtils
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.ItemCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class AnalysisHolder(
  private val binding: ItemCryptosTradingviewAnalysisBinding,
  private val ticker: (String, (TickerInfo, Context) -> Unit) -> Unit,
) : BaseListViewHolder<AnalysisInfo, ItemCryptosTradingviewAnalysisBinding>(binding) {

  override fun onBind(model: AnalysisInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    binding.tvRecommendation.text = model.summary.recommendation
    ticker(model.symbol) {ticker: TickerInfo, context: Context ->
      if (ticker.price == 0f) {
        binding.tvPrice.text = "--"
      } else {
        binding.tvPrice.text = ticker.price.toString()
      }

      if (ticker.state == 1) {
        binding.tvPrice.animation = AnimationUtils.loadAnimation(context, R.anim.blink_up)
      } else if (ticker.state == 2) {
        binding.tvPrice.animation = AnimationUtils.loadAnimation(context, R.anim.blink_down)
      }
    }
  }
}