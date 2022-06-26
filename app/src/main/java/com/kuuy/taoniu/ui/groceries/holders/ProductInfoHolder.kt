package com.kuuy.taoniu.ui.groceries.holders

import com.kuuy.taoniu.data.groceries.models.ProductInfo
import com.kuuy.taoniu.databinding.ItemGroceriesProductInfoBinding

import com.kuuy.taoniu.ui.base.BaseListViewHolder

class ProductInfoHolder(
  private val binding: ItemGroceriesProductInfoBinding
) : BaseListViewHolder<ProductInfo, ItemGroceriesProductInfoBinding>(binding) {

  override fun onBind(model: ProductInfo, position: Int) {
    binding.tvTitle.text = model.title
    binding.tvPrice.text = model.price.toString()
  }
}

