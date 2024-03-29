package com.kuuy.taoniu.ui.cryptos.holders.binance.spot.trade

import android.graphics.Color
import android.util.TypedValue
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.kuuy.taoniu.databinding.WidgesCryptosBinanceSpotTradeTabPagerBinding
import com.kuuy.taoniu.ui.base.BaseListViewHolder
import com.kuuy.taoniu.utils.DensityUtil

class TabPagerHolder(
  private val binding: WidgesCryptosBinanceSpotTradeTabPagerBinding,
  private val initRecyclerView: (RecyclerView, Int) -> Unit,
  private val initTextView: (TextView, Int) -> Unit,
) : BaseListViewHolder<Boolean, WidgesCryptosBinanceSpotTradeTabPagerBinding>(binding) {
  override fun onBind(model: Boolean, position: Int) {
    if (binding.root.childCount == 0) {
      if (position == 3) {
        addTextView()
      } else {
        addRecyclerView()
      }
    }
    val view = binding.root.children.first()
    if (view is RecyclerView) {
      view.adapter = null
      view.clearOnScrollListeners()
    }
    if (model) {
      if (view is TextView) {
        initTextView(view, position)
      } else if (view is RecyclerView) {
        initRecyclerView(view, position)
      }
    }
  }

  private fun addRecyclerView() {
    val recyclerView = RecyclerView(binding.root.context).apply {
      layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.MATCH_PARENT
      )
      (layoutParams as ConstraintLayout.LayoutParams).bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    }
    binding.root.addView(recyclerView)
  }

  private fun addTextView() {
    val textview = TextView(binding.root.context).apply {
      layoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.MATCH_PARENT
      )
      (layoutParams as ConstraintLayout.LayoutParams).apply {
        var paddingSize = DensityUtil.dp2px(binding.root.context, 5f)
        setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        setBackgroundColor(Color.WHITE)
        bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
      }
      text = "虚似币详解..."
    }
    binding.root.addView(textview)
  }
}