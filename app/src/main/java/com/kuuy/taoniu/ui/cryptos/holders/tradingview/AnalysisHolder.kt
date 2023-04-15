package com.kuuy.taoniu.ui.cryptos.holders.tradingview

import android.graphics.Typeface
import android.view.animation.AnimationUtils
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.ItemCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class AnalysisHolder(
  private val binding: ItemCryptosTradingviewAnalysisBinding,
  private val ticker: (String, (Ticker) -> Unit) -> Unit,
) : BaseListViewHolder<AnalysisInfo, ItemCryptosTradingviewAnalysisBinding>(binding) {
  override fun onBind(model: AnalysisInfo, position: Int) {
    binding.tvSymbol.text = model.symbol

    binding.tvRecommendation.typeface = Typeface.createFromAsset(binding.root.context.assets, "fonts/iconfont.ttf")
    if (model.summary.recommendation == "BUY" || model.summary.recommendation == "STRONG_BUY") {
      binding.tvRecommendation.let{
        if (model.summary.recommendation == "STRONG_BUY") {
          it.text = "\ue621\ue621"
        } else {
          it.text = "\ue621"
        }
        it.setTextColor(binding.root.context.getColor(R.color.material_green300))
      }
    } else if (model.summary.recommendation == "SELL" || model.summary.recommendation == "STRONG_SELL") {
      binding.tvRecommendation.let{
        if (model.summary.recommendation == "STRONG_SELL") {
          it.text = "\ue622\uE622"
        } else {
          it.text = "\ue622"
        }
        it.setTextColor(binding.root.context.getColor(R.color.material_red300))
      }
    }

    ticker(model.symbol) {ticker ->
      if (ticker.price == 0f) {
        binding.tvPrice.text = "--"
        binding.tvPercent.let{
          it.text = "--"
          it.background = binding.root.context.getDrawable(R.drawable.bg_percent_gray)
        }
      } else {
        binding.tvPrice.text = ticker.price.toString()
        if (ticker.change > 0) {
          binding.tvPercent.let{
            it.text ="+%.2f%%".format(ticker.change)
            it.background = binding.root.context.getDrawable(R.drawable.bg_percent_green)
          }
        } else {
          binding.tvPercent.let{
            if (ticker.change < 0) {
              it.text ="%.2f%%".format(ticker.change)
              it.background = binding.root.context.getDrawable(R.drawable.bg_percent_red)
            } else {
              it.text = "0.00%"
              it.background = binding.root.context.getDrawable(R.drawable.bg_percent_gray)
            }
          }
        }
      }

      if (ticker.state != 0) {
        binding.tvPrice.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.blink)
      }

      if (ticker.changeState != 0) {
        binding.tvPercent.animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.blink)
      }
    }
  }
}