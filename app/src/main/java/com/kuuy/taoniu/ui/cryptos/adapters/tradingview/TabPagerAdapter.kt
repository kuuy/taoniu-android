package com.kuuy.taoniu.ui.cryptos.adapters.tradingview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.databinding.WidgesCryptosTradingviewTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseTabPagerAdapter
import com.kuuy.taoniu.ui.cryptos.holders.tradingview.TabPagerHolder

class TabPagerAdapter(
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
) : BaseTabPagerAdapter<TabPagerHolder>() {
  override fun viewHolder(parent: ViewGroup): TabPagerHolder {
    var binding = WidgesCryptosTradingviewTabPagerBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return TabPagerHolder(binding, initRecyclerView)
  }

  override fun onBind(holder: TabPagerHolder, position: Int) {
    holder.onBind(listings[position], position)
  }
}