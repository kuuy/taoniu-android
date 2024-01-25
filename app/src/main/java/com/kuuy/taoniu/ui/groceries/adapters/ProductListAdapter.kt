package com.kuuy.taoniu.ui.groceries.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import com.kuuy.taoniu.utils.DiffUtils
import com.kuuy.taoniu.data.groceries.models.ProductInfo
import com.kuuy.taoniu.databinding.ItemGroceriesProductInfoBinding
import com.kuuy.taoniu.ui.base.BaseListAdapter
import com.kuuy.taoniu.ui.groceries.holders.ProductInfoHolder

class ProductListAdapter(
  private val onItemClicked: (ProductInfo) -> Unit
) : BaseListAdapter<ProductInfo, ProductInfoHolder>() {
  override fun viewHolder(parent: ViewGroup): ProductInfoHolder {
    var binding = ItemGroceriesProductInfoBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return ProductInfoHolder(binding)
  }

  override fun onBind(holder: ProductInfoHolder, position: Int) {
    var model = listings[position]
    holder.onBind(model, position)
    holder.itemView.setOnClickListener {
      onItemClicked(model)
    }
  }

  fun setData(products: List<ProductInfo>) {
    val diffUtil = DiffUtils(listings, products)
    val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
    listings = products as MutableList<ProductInfo>
    diffUtilResult.dispatchUpdatesTo(this)
  }

}

