package com.kuuy.taoniu.ui.widgets.pager

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kuuy.taoniu.ui.base.BaseTabPagerAdapter

class TabPager<T> @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr), LifecycleEventObserver {
  private val tabLayout by lazy {
    TabLayout(context).apply {
      id = View.generateViewId()
      layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
      tabGravity = TabLayout.GRAVITY_FILL
    }
  }
  private val viewPager by lazy {
    ViewPager2(context).apply {
      id = View.generateViewId()
      layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 0)
    }
  }
  var adapter: BaseTabPagerAdapter<*>? = null
    set(value) { field = value; viewPager.adapter = value }
  private var compositePageTransformer: CompositePageTransformer? = null

  private val callback = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      super.onPageSelected(position)
      adapter!!.position.postValue(position)
    }
  }

  init {
    tabLayout.apply {
      (layoutParams as LayoutParams).topToTop = LayoutParams.PARENT_ID
      (layoutParams as LayoutParams).bottomToTop = viewPager.id
      (layoutParams as LayoutParams).verticalBias = 0f
    }
    viewPager.apply {
      (layoutParams as LayoutParams).topToBottom = tabLayout.id
      (layoutParams as LayoutParams).bottomToBottom = LayoutParams.PARENT_ID
    }
    addView(tabLayout)
    addView(viewPager)
    viewPager.unregisterOnPageChangeCallback(callback)
    viewPager.registerOnPageChangeCallback(callback)
    compositePageTransformer = CompositePageTransformer()
    viewPager.setPageTransformer(compositePageTransformer)
  }

  fun tabs(tabs: Array<String>, mode: Int, lifecycleOwner: LifecycleOwner) {
    tabLayout.tabMode = mode
    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
      if (position < tabs.size) {
        tab.text = tabs[position]
      }
    }.attach()
    adapter?.size(tabs.size, lifecycleOwner)
  }

  override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
  }
}