package com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.tradings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.databinding.WidgesCryptosBinanceSpotTradingsSymbolsBinding
import com.kuuy.taoniu.ui.base.BaseTabPagerAdapter
import com.kuuy.taoniu.ui.cryptos.holders.binance.spot.tradings.TabPagerHolder

class TabPagerAdapter(
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
) : BaseTabPagerAdapter<TabPagerHolder>() {
  override fun viewHolder(parent: ViewGroup): TabPagerHolder {
    var binding = WidgesCryptosBinanceSpotTradingsSymbolsBinding.inflate(
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