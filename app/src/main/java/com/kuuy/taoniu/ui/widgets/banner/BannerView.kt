package com.kuuy.taoniu.ui.widgets.banner

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

import com.kuuy.taoniu.R
import com.kuuy.taoniu.ui.base.BaseBannerAdapter

class BannerView<T> @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet?,
  defStyleAttr: Int = 0
): RelativeLayout(context, attrs, defStyleAttr), LifecycleEventObserver {
  private val viewPager by lazy {
    ViewPager2(context).apply {
      layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }
  }
  private val indicatorLayout by lazy {
    LinearLayout(context).apply {
      layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
      (layoutParams as LayoutParams).addRule(ALIGN_PARENT_BOTTOM)
      gravity = Gravity.CENTER
    }
  }
  var adapter: BaseBannerAdapter<T, *>? = null
      set(value) { field = value; viewPager.adapter = value }
  private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
  private var onPageClickListener: BaseBannerAdapter.OnPageClickListener? = null
  private var compositePageTransformer: CompositePageTransformer? = null
  private var marginPageTransformer: MarginPageTransformer? = null

  private var lastPosition = 0
  private var listSize = 0

  private val play: Runnable by lazy {
    Runnable {
      val currentItem = viewPager.currentItem
      if (currentItem == listSize - 1) {
        viewPager.setCurrentItem(0, false)
      } else {
        viewPager.currentItem = currentItem + 1
      }
      postDelayed(play, interval)
    }
  }

  private var interval = 3000L

  var isAutoPlay = false
      set(value) { field = value }

  var isCanLoop = false
      set(value) { field=value; adapter?.isCanLoop=value }

  private var isShowIndicator = false

  private var pageMargin = 0

  private var revealWidth = -1

  private var offscreenPageLimit = 3

  private var indicatorMargin = dpToPx(5)

  private var normalImage = R.drawable.main_shape_bvp_dot

  private var checkedImage = R.drawable.main_shape_bvp_dot_selected

  private val callback = object : ViewPager2.OnPageChangeCallback() {
    override fun onPageScrolled(
      position: Int,
      positionOffset: Float,
      positionOffsetPixels: Int
    ) {
      super.onPageScrolled(position, positionOffset, positionOffsetPixels)
      val realPosition: Int = adapter!!.getRealPosition(position)
      onPageChangeCallback?.onPageScrolled(
        realPosition,
        positionOffset,
        positionOffsetPixels,
      )
    }

    override fun onPageSelected(position: Int) {
      super.onPageSelected(position)
      val realPosition: Int = adapter!!.getRealPosition(position)
      onPageChangeCallback?.onPageSelected(realPosition)
      setIndicatorDots(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
      super.onPageScrollStateChanged(state)
      onPageChangeCallback?.onPageScrollStateChanged(state)
    }
  }

  init {
    addView(viewPager)
    addView(indicatorLayout)
    viewPager.unregisterOnPageChangeCallback(callback)
    viewPager.registerOnPageChangeCallback(callback)
    compositePageTransformer = CompositePageTransformer()
    viewPager.setPageTransformer(compositePageTransformer)
  }

  private fun initBannerData(list: List<T>) {
    if (list.isNotEmpty()) {
      initIndicatorDots(list)
      setupViewPager()
    }
  }

  private fun initIndicatorDots(list: List<T>) {
    indicatorLayout.removeAllViews()
    if (isShowIndicator && listSize > 1) {
      for (i in list.indices) {
        val imageView = ImageView(context)
        if (i == 0) {
          imageView.setBackgroundResource(checkedImage)
          imageView.setBackgroundResource(checkedImage)
        } else {
          imageView.setBackgroundResource(normalImage)
        }
        val layoutParams = LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT,
          LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(
          indicatorMargin,
          indicatorMargin,
          indicatorMargin,
          indicatorMargin
        )
        imageView.layoutParams = layoutParams
        indicatorLayout.addView(imageView)
      }
    }
  }

  private fun setIndicatorDots(position: Int) {
    if (isShowIndicator && listSize > 1) {
      val current = position % listSize
      val last = lastPosition % listSize
      indicatorLayout.getChildAt(last).setBackgroundResource(normalImage)
      indicatorLayout.getChildAt(current).setBackgroundResource(checkedImage)
      lastPosition = position
    }
  }

  private fun setupViewPager() {
    if (adapter == null) {
      throw NullPointerException("You must set adapter for BannerView")
    }

    if (revealWidth != -1) {
      val recyclerView = viewPager.getChildAt(0) as RecyclerView
      recyclerView.setPadding(pageMargin + revealWidth, 0, pageMargin + revealWidth, 0)
      recyclerView.clipToPadding = false
    }

    adapter!!.pageClickListener = onPageClickListener
    resetCurrentItem()

    viewPager.offscreenPageLimit = offscreenPageLimit
    startTimer()
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
    if (isAutoPlay && adapter != null && listSize > 1) {
      stopTimer()
      postDelayed(play, interval)
    }
  }

  private fun stopTimer() {
    removeCallbacks(play)
  }

  fun setOnPageSelectedCallBack(
    onPageChangeCallback: ViewPager2.OnPageChangeCallback,
  ) {
    this.onPageChangeCallback = onPageChangeCallback
  }

  fun lifecycleRegistry(
    lifecycleRegistry: Lifecycle,
  ): BannerView<T> {
    lifecycleRegistry.addObserver(this)
    return this
  }

  fun setAutoPlay(isAutoPlay: Boolean): BannerView<T> {
    this.isAutoPlay = isAutoPlay
    return this
  }

  fun setCanLoop(isCanLoop: Boolean): BannerView<T> {
    this.isCanLoop = isCanLoop
    return this
  }

  fun setInterval(int: Int): BannerView<T> {
    interval = int * 1000L
    return this
  }

  fun setOffscreenPageLimit(int: Int): BannerView<T> {
    offscreenPageLimit = int
    return this
  }

  fun setCanShowIndicator(bool: Boolean): BannerView<T> {
    isShowIndicator = bool
    return this
  }

  fun setPageTransformer(transformer: ViewPager2.PageTransformer): BannerView<T> {
    viewPager.setPageTransformer(transformer)
    return this
  }

  fun addPageTransformer(transformer: ViewPager2.PageTransformer): BannerView<T> {
    compositePageTransformer?.addTransformer(transformer)
    return this
  }

  fun removeTransformer(transformer: ViewPager2.PageTransformer) {
    compositePageTransformer?.removeTransformer(transformer)
  }


  fun removeMarginPageTransformer() {
    if (marginPageTransformer != null) {
      compositePageTransformer?.removeTransformer(marginPageTransformer!!)
    }
  }

  fun setPageMargin(margin: Int): BannerView<T> {
    pageMargin = dpToPx(margin)
    removeMarginPageTransformer()
    marginPageTransformer = MarginPageTransformer(pageMargin)
    compositePageTransformer?.addTransformer(marginPageTransformer!!)
    return this
  }

  fun setRevealWidth(int: Int): BannerView<T> {
    revealWidth = dpToPx(int)
    return this
  }

  fun setOnPageClickListener(onPageClickListener: BaseBannerAdapter.OnPageClickListener): BannerView<T> {
    this.onPageClickListener = onPageClickListener
    return this
  }

  fun setIndicatorMargin(margin: Int): BannerView<T> {
    indicatorMargin = margin
    return this
  }

  fun setIndicatorSliderColor(
      @DrawableRes normal: Int,
      @DrawableRes checked: Int
  ): BannerView<T> {
    normalImage = normal
    checkedImage = checked
    return this
  }

  fun setData(listings: List<T>) {
    listSize = listings.size
    adapter?.setData(listings)
    initBannerData(listings)
  }

  fun setUpCurrentItem(item: Int, smoothScroll: Boolean) {
    if (isCanLoop && listSize > 1) {
      val currentItem = viewPager.currentItem
      val realPosition: Int = adapter!!.getRealPosition(currentItem)
      if (currentItem != item) {
        if (item == 0 && realPosition == listSize - 1) {
          viewPager.setCurrentItem(currentItem + 1, smoothScroll)
        } else if (realPosition == 0 && item == listSize - 1) {
          viewPager.setCurrentItem(currentItem - 1, smoothScroll)
        } else {
          viewPager.setCurrentItem(currentItem + (item - realPosition), smoothScroll)
        }
      }
    } else {
      viewPager.setCurrentItem(item, smoothScroll)
    }
  }

  fun refreshData(list: List<T>) {
    if (adapter != null && list.isNotEmpty()) {
      stopTimer()
      listSize = list.size
      adapter!!.setData(list)
      adapter!!.notifyDataSetChanged()
      resetCurrentItem()
      initIndicatorDots(list)
      startTimer()
    }
  }

  private fun dpToPx(dip: Int): Int {
    return (0.5f + dip * Resources.getSystem().displayMetrics.density).toInt()
  }

  private fun resetCurrentItem() {
    if (listSize > 1 && isCanLoop) {
      lastPosition = Int.MAX_VALUE / 2 - ((Int.MAX_VALUE / 2) % listSize)
      viewPager.setCurrentItem(lastPosition, false)
    } else {
      viewPager.setCurrentItem(0, false)
    }
  }
}
