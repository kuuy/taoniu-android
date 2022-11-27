package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.plans

import android.graphics.Typeface
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.cryptos.models.binance.spot.plans.DailyInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotPlansDailyBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class DailyHolder(
  private val binding: ItemCryptosBinanceSpotPlansDailyBinding,
) : BaseListViewHolder<DailyInfo, ItemCryptosBinanceSpotPlansDailyBinding>(binding) {
  override fun onBind(model: DailyInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    binding.tvRecommendation.typeface = Typeface.createFromAsset(binding.root.context.assets, "fonts/iconfont.ttf")
    if (model.side == 1) {
      binding.tvRecommendation.let{
        it.text = "\ue621"
        it.setTextColor(binding.root.context.getColor(R.color.material_red300))
      }
    } else if (model.side == 2) {
      binding.tvRecommendation.let{
        it.text = "\ue622"
        it.setTextColor(binding.root.context.getColor(R.color.material_green300))
      }
    }
    binding.tvPrice.text = "${model.price}x${model.amount}"
    binding.tvTimestamp.text = model.timestampFormat
  }
}