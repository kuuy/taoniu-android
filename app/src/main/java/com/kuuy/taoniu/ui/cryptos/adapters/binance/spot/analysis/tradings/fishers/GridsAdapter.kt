package com.kuuy.taoniu.ui.cryptos.adapters.binance.spot.analysis.tradings.fishers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kuuy.taoniu.data.cryptos.models.binance.spot.SymbolFilterInfo
import com.kuuy.taoniu.data.cryptos.models.binance.spot.analysis.tradings.fishers.GridInfo
import com.kuuy.taoniu.databinding.ItemCryptosBinanceSpotAnalysisTradingsFishersGridsBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.binance.spot.analysis.tradings.fishers.GridsHolder

class GridsAdapter: BaseListAdapter<GridInfo, GridsHolder>() {
  var filterListings: ArrayList<SymbolFilterInfo>? = null

  override fun getItemCount(): Int {
    if (filterListings != null) {
      return filterListings!!.size
    }
    return listings.size
  }

  override fun viewHolder(parent: ViewGroup): GridsHolder {
    var binding = ItemCryptosBinanceSpotAnalysisTradingsFishersGridsBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return GridsHolder(binding)
  }

  override fun onBind(holder: GridsHolder, position: Int) {
    val realPosition = if (filterListings != null) {
      filterListings!![position].position
    } else {
      position
    }
    val model = listings[realPosition]
    holder.onBind(model, position)
  }
}