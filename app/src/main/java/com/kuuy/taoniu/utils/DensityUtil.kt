package com.kuuy.taoniu.utils

import android.content.Context
import android.util.TypedValue

object DensityUtil {
  fun dp2px(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      dp,
      context.resources.displayMetrics,
    ).toInt()
  }

  fun sp2px(context: Context, sp: Float): Float {
    return TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_SP,
      sp,
      context.resources.displayMetrics,
    )
  }
}