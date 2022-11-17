package com.kuuy.taoniu.ui.cryptos.adapters.tradingview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.ItemCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.tradingview.AnalysisHolder
import com.kuuy.taoniu.utils.DiffUtils

class AnalysisAdapter: BaseListAdapter<AnalysisInfo, AnalysisHolder>() {

  override fun viewHolder(parent: ViewGroup): AnalysisHolder {
    var binding = ItemCryptosTradingviewAnalysisBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return AnalysisHolder(binding)
  }

  override fun onBind(holder: AnalysisHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  fun setData(items: List<AnalysisInfo>) {
    val diffUtil = DiffUtils<AnalysisInfo>(listings, items)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    listings = items
    diffUtilResult.dispatchUpdatesTo(this)
  }

}