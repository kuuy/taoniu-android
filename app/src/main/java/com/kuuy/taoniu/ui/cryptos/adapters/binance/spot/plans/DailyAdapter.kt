package com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.plans

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kuuy.taoniu.data.cryptos.models.binance.spot.plans.DailyInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotPlansDailyBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.binance.spot.plans.DailyHolder
import com.kuuy.taoniu.utils.DiffUtils

class DailyAdapter: BaseListAdapter<DailyInfo, DailyHolder>() {
  override fun viewHolder(parent: ViewGroup): DailyHolder {
    var binding = ItemCryptosBinanceSpotPlansDailyBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return DailyHolder(binding)
  }

  override fun onBind(holder: DailyHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  fun setDatas(items: List<DailyInfo>) {
    val diffUtil = DiffUtils(listings, items)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    diffUtilResult.dispatchUpdatesTo(this)

    listings = items
  }

  fun addDatas(items: List<DailyInfo>) {
    val datas = mutableListOf<DailyInfo>()
    datas.addAll(listings)
    datas.addAll(items)

    val diffUtil = DiffUtils(listings, datas)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    diffUtilResult.dispatchUpdatesTo(this)

    listings = datas
  }
}