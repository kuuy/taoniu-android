package com.kuuy.taoniu.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class OnScrollListener(
  private val layoutManager: RecyclerView.LayoutManager,
  private val onLoadMoreListener: () -> Unit,
): RecyclerView.OnScrollListener() {
  var previousTotal = 0
  var loading = true
  val visibleThreshold = 1
  var firstVisibleItem = 0
  var visibleItemCount = 0
  var totalItemCount = 0

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    visibleItemCount = recyclerView.childCount
    totalItemCount = layoutManager.itemCount
    when (layoutManager) {
      is LinearLayoutManager -> {
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
      }
      is StaggeredGridLayoutManager -> {
        val positions = layoutManager.findFirstVisibleItemPositions(null)
        firstVisibleItem = positions.min()
      }
      else -> {}
    }

    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false
        previousTotal = totalItemCount
      }
    }
  }

  override fun onScrollStateChanged(recyclerView: RecyclerView, state: Int) {
    when (state) {
      RecyclerView.SCROLL_STATE_IDLE -> {
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
          loading = true
          onLoadMoreListener()
        }
      }
    }
  }
}