package com.kuuy.taoniu.ui.groceries.holders

import coil.load
import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.R
import com.kuuy.taoniu.data.groceries.models.ProductInfo
import com.kuuy.taoniu.databinding.ItemGroceriesProductInfoBinding

import com.kuuy.taoniu.ui.base.BaseListViewHolder

class ProductInfoHolder(
  private val binding: ItemGroceriesProductInfoBinding
) : BaseListViewHolder<ProductInfo, ItemGroceriesProductInfoBinding>(binding) {
  override fun onBind(model: ProductInfo, position: Int) {
    binding.tvTitle.text = model.title
    if (model.cover.isNotBlank()) {
      binding.ivCover.load("%s%s".format(
        BuildConfig.STORAGES_URL,
        model.cover
      )) {
        crossfade(true)
        error(R.drawable.ic_no_image)
      }
    } else {
      binding.ivCover.load(R.drawable.ic_no_image)
    }
    binding.tvPrice.text = model.price.toString()
  }

  companion object {
    private const val TAG = "PRODUCT_INFO_HOLDER"
  }
}

