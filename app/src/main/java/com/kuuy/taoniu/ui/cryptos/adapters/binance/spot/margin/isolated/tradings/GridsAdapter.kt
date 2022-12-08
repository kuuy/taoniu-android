package com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.margin.isolated.tradings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.OrderInfo
import com.kuuy.taoniu.data.cryptos.models.binance.spot.margin.isolated.tradings.GridInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotMarginIsolatedTradingsGridsBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.binance.spot.margin.isolated.tradings.GridsHolder
import com.kuuy.taoniu.utils.DiffUtils

class GridsAdapter(
  private val onItemClicked: (GridInfo) -> Unit,
): BaseListAdapter<GridInfo, GridsHolder>() {
  override fun viewHolder(parent: ViewGroup): GridsHolder {
    var binding = ItemCryptosBinanceSpotMarginIsolatedTradingsGridsBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return GridsHolder(binding)
  }

  override fun onBind(holder: GridsHolder, position: Int) {
    val model = listings[position]
    holder.onBind(model, position)
    holder.itemView.setOnClickListener {
      onItemClicked(model)
    }
  }
}