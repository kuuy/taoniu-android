package com.kuuy.taoniu.ui.cryptos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import com.kuuy.taoniu.utils.DiffUtils
import com.kuuy.taoniu.data.cryptos.models.CoinInfo
import com.kuuy.taoniu.databinding.ItemCryptosCoinInfoBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.CoinInfoHolder

class CoinListAdapter
    : BaseListAdapter<CoinInfo, CoinInfoHolder>() {

  override fun viewHolder(parent: ViewGroup): CoinInfoHolder {
    var binding = ItemCryptosCoinInfoBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return CoinInfoHolder(binding)
  }

  override fun onBind(holder: CoinInfoHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  fun setData(coins: List<CoinInfo>) {
    val diffUtil = DiffUtils<CoinInfo>(listings, coins)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    listings = coins as MutableList<CoinInfo>
    diffUtilResult.dispatchUpdatesTo(this)
  }

}

