package com.kuuy.taoniu.ui.cryptos.adapters.tradingview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.data.cryptos.models.TickerInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.WidgesCryptosTradingviewTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseTabPagerAdapter
import com.kuuy.taoniu.ui.cryptos.holders.tradingview.TabPagerHolder
import com.kuuy.taoniu.utils.DiffUtils

class TabPagerAdapter(
  private val initRecycler: (RecyclerView) -> Unit,
) : BaseTabPagerAdapter<Nothing?, TabPagerHolder>() {
  override fun viewHolder(parent: ViewGroup): TabPagerHolder {
    var binding = WidgesCryptosTradingviewTabPagerBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return TabPagerHolder(binding, initRecycler)
  }

  override fun onRealBind(holder: TabPagerHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
  }

  @SuppressLint("NotifyDataSetChanged")
  override fun initDatas(size: Int) {
    listings = (1..size).map { null }
    notifyDataSetChanged()
  }
}