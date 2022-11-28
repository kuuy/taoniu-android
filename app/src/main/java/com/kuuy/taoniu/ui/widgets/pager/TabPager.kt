package com.kuuy.taoniu.ui.widgets.pager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
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
      tabMode = TabLayout.MODE_SCROLLABLE
      tabGravity = TabLayout.GRAVITY_FILL
    }
  }
  private val viewPager by lazy {
    ViewPager2(context).apply {
      id = View.generateViewId()
      layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
  }
  var adapter: BaseTabPagerAdapter<T, *>? = null
    set(value) { field = value; viewPager.adapter = value }
  private var compositePageTransformer: CompositePageTransformer? = null

  private val callback = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
      super.onPageSelected(position)
      val realPosition = adapter!!.getRealPosition(position)
      adapter!!.activatePosition.postValue(realPosition)
      scrollTo(0, 0)
    }
  }

  init {
    tabLayout.apply {
      (layoutParams as LayoutParams).topToTop = LayoutParams.PARENT_ID
      (layoutParams as LayoutParams).bottomToTop = viewPager.id
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

  override fun onStateChanged(
    source: LifecycleOwner,
    event: Lifecycle.Event,
  ) {
    when(event) {
      Lifecycle.Event.ON_START -> startTimer()
      Lifecycle.Event.ON_STOP -> stopTimer()
      else -> {}
    }
  }

  override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    when (ev.action) {
      MotionEvent.ACTION_DOWN -> {
        stopTimer()
      }
      MotionEvent.ACTION_UP,
      MotionEvent.ACTION_CANCEL,
      MotionEvent.ACTION_OUTSIDE -> {
        startTimer()
      }
    }

    return super.dispatchTouchEvent(ev)
  }

  private fun startTimer() {
  }

  private fun stopTimer() {
  }

  fun tabs(tabs: Array<String>) {
    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
      tab.text = tabs[position]
    }.attach()
    adapter?.initDatas(tabs.size)
  }

  fun lifecycleRegistry(
    lifecycleRegistry: Lifecycle,
  ): TabPager<T> {
    lifecycleRegistry.addObserver(this)
    return this
  }
}