package com.kuuy.taoniu.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.kuuy.taoniu.R

class LabelItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr), LifecycleEventObserver {
  var value: Any = "--"
    set(value) {
      tvValue.text = value.toString()
      field = value
    }

  private val tvLable by lazy {
    TextView(context).apply {
      id = View.generateViewId()
      layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
  }
  private val tvValue by lazy {
    TextView(context).apply {
      id = View.generateViewId()
      layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
  }

  init {
    tvLable.apply {
      (layoutParams as LayoutParams).topToTop = LayoutParams.PARENT_ID
      (layoutParams as LayoutParams).bottomToTop = tvValue.id
      (layoutParams as LayoutParams).verticalBias = 0f
    }
    tvValue.apply {
      (layoutParams as LayoutParams).topToBottom = tvLable.id
      (layoutParams as LayoutParams).bottomToBottom = LayoutParams.PARENT_ID
      (layoutParams as LayoutParams).topMargin = 4
    }
    tvLable.setTextColor(context.getColor(R.color.md_grey_800))
    tvValue.setTextColor(context.getColor(R.color.primary_text))
    tvLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
    tvValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
    addView(tvLable)
    addView(tvValue)
    attrs?.run {
      for (i in 0 until attributeCount) {
        val attrName = getAttributeName(i)
        when (attrName) {
          "label" -> {
            tvLable.text = getAttributeValue(i)
          }
          "value" -> {
            tvValue.text = getAttributeValue(i)
          }
        }
      }
    }
  }

  override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
  }
}