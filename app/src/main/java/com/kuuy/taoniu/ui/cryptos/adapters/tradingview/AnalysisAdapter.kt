package com.kuuy.taoniu.ui.cryptos.adapters.tradingview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.ItemCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.tradingview.AnalysisHolder
import com.kuuy.taoniu.utils.DiffUtils

class AnalysisAdapter(
  private val ticker: (String, (TickerInfo) -> Unit) -> Unit,
): BaseListAdapter<AnalysisInfo, AnalysisHolder>() {
  override fun viewHolder(parent: ViewGroup): AnalysisHolder {
    var binding = ItemCryptosTradingviewAnalysisBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return AnalysisHolder(binding, ticker)
  }

  override fun onBind(holder: AnalysisHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  @SuppressLint("NotifyDataSetChanged")
  fun clear() {
    listings = listOf()
    notifyDataSetChanged()
  }

  fun addDatas(items: List<AnalysisInfo>) {
    val datas = mutableListOf<AnalysisInfo>()
    datas.addAll(listings)
    datas.addAll(items)

    val diffUtil = DiffUtils(listings, datas)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)

    listings = datas
    diffUtilResult.dispatchUpdatesTo(this)
  }
}