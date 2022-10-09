package com.kuuy.taoniu.ui.groceries.dialogs

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import android.view.View
import android.view.ViewGroup

import com.kuuy.taoniu.R
import com.kuuy.taoniu.ui.base.BaseSheetDialog
import com.kuuy.taoniu.databinding.DialogGroceriesCounterOrderBinding

class CounterOrderDialog
  : BaseSheetDialog<DialogGroceriesCounterOrderBinding>() {

  protected override fun viewBinding(container: ViewGroup?)
      : DialogGroceriesCounterOrderBinding {
    return DialogGroceriesCounterOrderBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
    binding.ivClose.setOnClickListener {
      dismiss()
    }
  }
}
