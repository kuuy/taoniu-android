package com.kuuy.taoniu.ui.cryptos.holders

import com.kuuy.taoniu.databinding.ItemCryptosBannerBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder

class BannerHolder(
  private val binding: ItemCryptosBannerBinding
) : BaseListViewHolder<Int, ItemCryptosBannerBinding>(binding) {

  override fun onBind(model: Int, position: Int) {
    binding.ivBanner.setImageResource(model)
  }
}
