package com.kuuy.taoniu.ui.cryptos.adapters.tradingview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.data.cryptos.models.binance.spot.SymbolFilterInfo
import com.kuuy.taoniu.data.cryptos.models.tradingview.AnalysisInfo
import com.kuuy.taoniu.databinding.ItemCryptosTradingviewAnalysisBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.tradingview.AnalysisHolder

class AnalysisAdapter(
  private val ticker: (String, (Ticker) -> Unit) -> Unit,
  private val onItemClicked: (AnalysisInfo) -> Unit,
): BaseListAdapter<AnalysisInfo, AnalysisHolder>(), Filterable {
  var filterListings: ArrayList<SymbolFilterInfo>? = null

  override fun getItemCount(): Int {
    if (filterListings != null) {
      return filterListings!!.size
    }
    return listings.size
  }

  override fun viewHolder(parent: ViewGroup): AnalysisHolder {
    var binding = ItemCryptosTradingviewAnalysisBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return AnalysisHolder(binding, ticker)
  }

  override fun onBind(holder: AnalysisHolder, position: Int) {
    val realPosition = if (filterListings != null) {
      filterListings!![position].position
    } else {
      position
    }
    var model = listings[realPosition]
    holder.onBind(model, position)
    holder.itemView.setOnClickListener {
      onItemClicked(model)
    }
  }

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun performFiltering(constraint: CharSequence?): FilterResults {
        if (constraint.isNullOrEmpty()) {
          filterListings = null
        } else {
          filterListings = ArrayList()
          listings.forEachIndexed { i, dailyInfo ->
            if (dailyInfo.symbol.contains(constraint.toString(), true)) {
              filterListings!!.add(SymbolFilterInfo(dailyInfo.symbol, i))
            }
          }
        }
        val filterResults = FilterResults()
        filterResults.values = filterListings
        return filterResults
      }

      @SuppressLint("NotifyDataSetChanged")
      override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        filterListings = results?.values as ArrayList<SymbolFilterInfo>?
        notifyDataSetChanged()
      }
    }
  }
}