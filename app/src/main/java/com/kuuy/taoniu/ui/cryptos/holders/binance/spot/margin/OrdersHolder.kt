package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.margin

import android.graphics.Typeface
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.OrderInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotMarginOrdersBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class OrdersHolder(
  private val binding: ItemCryptosBinanceSpotMarginOrdersBinding,
) : BaseListViewHolder<OrderInfo, ItemCryptosBinanceSpotMarginOrdersBinding>(binding) {
  override fun onBind(model: OrderInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    binding.tvRecommendation.typeface = Typeface.createFromAsset(binding.root.context.assets, "fonts/iconfont.ttf")
    if (model.side == "BUY") {
      binding.tvRecommendation.let{
        it.text = "\ue621"
        it.setTextColor(it.context.getColor(R.color.md_green_300))
      }
    } else if (model.side == "SELL") {
      binding.tvRecommendation.let{
        it.text = "\ue622"
        it.setTextColor(it.context.getColor(R.color.md_red_300))
      }
    }
    binding.tvPrice.text = "${model.price}x${model.quantity*model.price}"
    binding.tvStatus.text = model.status
    binding.tvTimestamp.text = model.timestampFormat
  }
}