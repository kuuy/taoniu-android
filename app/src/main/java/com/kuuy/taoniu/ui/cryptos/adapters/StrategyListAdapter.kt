package com.kuuy.taoniu.ui.cryptos.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil

import com.kuuy.taoniu.data.cryptos.models.StrategyInfo
import com.kuuy.taoniu.data.cryptos.models.binance.spot.SymbolFilterInfo
import com.kuuy.taoniu.databinding.ItemCryptosStrategyInfoBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.cryptos.holders.StrategyInfoHolder

class StrategyListAdapter
    : BaseListAdapter<StrategyInfo, StrategyInfoHolder>(), Filterable {
  var filterListings: ArrayList<SymbolFilterInfo>? = null

  override fun getItemCount(): Int {
    if (filterListings != null) {
      return filterListings!!.size
    }
    return listings.size
  }

  override fun viewHolder(parent: ViewGroup): StrategyInfoHolder {
    var binding = ItemCryptosStrategyInfoBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return StrategyInfoHolder(binding)
  }

  override fun onBind(holder: StrategyInfoHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
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

