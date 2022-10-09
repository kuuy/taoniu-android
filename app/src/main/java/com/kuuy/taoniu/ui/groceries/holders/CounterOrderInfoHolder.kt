package com.kuuy.taoniu.ui.groceries.holders

import com.kuuy.taoniu.data.groceries.models.CounterOrderInfo
import com.kuuy.taoniu.databinding.ItemGroceriesCounterOrderInfoBinding

import com.kuuy.taoniu.ui.base.BaseListViewHolder

class CounterOrderInfoHolder(
  private val binding: ItemGroceriesCounterOrderInfoBinding
) : BaseListViewHolder<CounterOrderInfo, ItemGroceriesCounterOrderInfoBinding>(binding) {

  override fun onBind(model: CounterOrderInfo, position: Int) {
    binding.tvTitle.text = model.title
    binding.tvPrice.text = "%,.2f".format(model.price)
    binding.tvQuantity.text = "%d x ".format(model.quantity)
    binding.tvAmount.text = "%,.2f".format(
      model.price * model.quantity,
    )
  }
}

