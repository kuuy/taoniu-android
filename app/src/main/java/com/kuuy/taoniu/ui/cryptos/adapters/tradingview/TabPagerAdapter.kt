package com.kuuy.taoniu.ui.cryptos.adapters.tradingview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.models.binance.spot.plans.DailyInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.WidgesCryptosTradingviewTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseTabPagerAdapter
import com.kuuy.taoniu.ui.cryptos.holders.tradingview.TabPagerHolder
import com.kuuy.taoniu.utils.DiffUtils
import com.kuuy.taoniu.utils.observeOnce

class TabPagerAdapter(
  private val initRecycler: (RecyclerView, Int) -> Unit,
) : BaseTabPagerAdapter<Boolean, TabPagerHolder>() {
  override fun viewHolder(parent: ViewGroup): TabPagerHolder {
    var binding = WidgesCryptosTradingviewTabPagerBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return TabPagerHolder(binding, initRecycler)
  }

  override fun onRealBind(holder: TabPagerHolder, position: Int) {
    holder.onBind(listings[position], position)
  }

  @SuppressLint("NotifyDataSetChanged")
  override fun initDatas(size: Int) {
    (0 until size).map {
      listings.add(false)
    }
    notifyDataSetChanged()
  }

  override fun activate(position: Int) {
    for (i in 0 until listings.size) {
      if (listings[i]) {
        listings[i] = false
        notifyItemChanged(i)
      }
    }
    listings[position] = true
    notifyItemChanged(position)
  }
}