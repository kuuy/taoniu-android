package com.kuuy.taoniu.ui.cryptos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import com.kuuy.taoniu.utils.DiffUtils
import com.kuuy.taoniu.databinding.ItemCryptosBannerBinding
import com.kuuy.taoniu.ui.base.BaseBannerAdapter
import com.kuuy.taoniu.ui.cryptos.holders.BannerHolder

class BannerAdapter : BaseBannerAdapter<Int, BannerHolder>() {
  override fun viewHolder(parent: ViewGroup): BannerHolder {
    var binding = ItemCryptosBannerBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return BannerHolder(binding)
  }

  override fun onRealBind(holder: BannerHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  override fun setData(banners: List<Int>) {
    val diffUtil = DiffUtils(listings, banners)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    listings = banners as MutableList<Int>
    diffUtilResult.dispatchUpdatesTo(this)
  }
}
