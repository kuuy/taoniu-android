package com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.analysis.tradings.fishers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.kuuy.taoniu.databinding.WidgesCryptosBinanceSpotAnalysisTradingsFishersGridsBinding
import com.kuuy.taoniu.ui.base.BaseTabPagerAdapter
import com.kuuy.taoniu.ui.cryptos.holders.binance.spot.analysis.tradings.fishers.TabPagerHolder

class TabPagerAdapter(
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
) : BaseTabPagerAdapter<TabPagerHolder>() {
  override fun viewHolder(parent: ViewGroup): TabPagerHolder {
    var binding = WidgesCryptosBinanceSpotAnalysisTradingsFishersGridsBinding.inflate(
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