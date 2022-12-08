package com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.trade

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.databinding.WidgesCryptosBinanceSpotTradeTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseTabPagerAdapter
import com.kuuy.taoniu.ui.cryptos.holders.binance.spot.trade.TabPagerHolder

class TabPagerAdapter(
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
  private val initTextView: (TextView, Int) -> Unit,
) : BaseTabPagerAdapter<TabPagerHolder>() {
  override fun viewHolder(parent: ViewGroup): TabPagerHolder {
    var binding = WidgesCryptosBinanceSpotTradeTabPagerBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return TabPagerHolder(binding, initRecyclerView, initTextView)
  }

  override fun onBind(holder: TabPagerHolder, position: Int) {
    holder.onBind(listings[position], position)
  }
}