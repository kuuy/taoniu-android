package com.kuuy.taoniu.ui.cryptos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import com.kuuy.taoniu.utils.DiffUtils
import com.kuuy.taoniu.data.cryptos.models.StrategyInfo
import com.kuuy.taoniu.databinding.ItemCryptosStrategyInfoBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.StrategyInfoHolder

class StrategyListAdapter
    : BaseListAdapter<StrategyInfo, StrategyInfoHolder>() {

  override fun viewHolder(parent: ViewGroup): StrategyInfoHolder {
    var binding = ItemCryptosStrategyInfoBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return StrategyInfoHolder(binding)
  }

  override fun onBind(holder: StrategyInfoHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  fun setData(strategies: List<StrategyInfo>) {
    val diffUtil = DiffUtils<StrategyInfo>(listings, strategies)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    listings = strategies
    diffUtilResult.dispatchUpdatesTo(this)
  }

}

