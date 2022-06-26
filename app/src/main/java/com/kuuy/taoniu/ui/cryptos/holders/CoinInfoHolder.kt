package com.kuuy.taoniu.ui.cryptos.holders

import coil.load

import com.kuuy.taoniu.R
import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.cryptos.models.CoinInfo
import com.kuuy.taoniu.databinding.ItemCryptosCoinInfoBinding

import com.kuuy.taoniu.ui.base.BaseListViewHolder

class CoinInfoHolder(
  private val binding: ItemCryptosCoinInfoBinding
) : BaseListViewHolder<CoinInfo, ItemCryptosCoinInfoBinding>(binding) {

  override fun onBind(model: CoinInfo, position: Int) {
    binding.tvSymbol.text = model.symbol
    binding.ivIcon.load("%sicons/coins/%s".format(
      BuildConfig.CRYPTOS_ASSETS_URL,
      model.icon
    )) {
      crossfade(100)
      error(R.drawable.ic_no_image)
    }

    var view = binding.root
    when(position) {
      0 -> {
        view.background = view.context.getDrawable(
          R.drawable.bg_rounded_seashell
        )
      }
      1 -> {
        view.background = view.context.getDrawable(
          R.drawable.bg_rounded_honeydew
        )
      }
      2 -> {
        view.background = view.context.getDrawable(
          R.drawable.bg_rounded_purplesmoke
        )
      }
      3 -> {
        view.background = view.context.getDrawable(
          R.drawable.bg_rounded_mistyrose
        )
      }
      else -> {
        view.background = view.context.getDrawable(
          R.drawable.bg_rounded_seashell
        )
      }
    }
  }
}

