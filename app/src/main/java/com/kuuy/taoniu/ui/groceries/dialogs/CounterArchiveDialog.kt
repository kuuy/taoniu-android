package com.kuuy.taoniu.ui.groceries.dialogs

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import android.view.View
import android.view.ViewGroup

import com.kuuy.taoniu.R
import com.kuuy.taoniu.ui.base.BaseSheetDialog
import com.kuuy.taoniu.databinding.DialogGroceriesCounterArchiveBinding

class CounterArchiveDialog
  : BaseSheetDialog<DialogGroceriesCounterArchiveBinding>() {

  protected override fun viewBinding(container: ViewGroup?)
      : DialogGroceriesCounterArchiveBinding {
    return DialogGroceriesCounterArchiveBinding.inflate(
      layoutInflater,
      container,
      false
    )
  }

  protected override fun onBind() {
  }
}
