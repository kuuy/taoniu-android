package com.kuuy.taoniu.data.groceries.models

data class FeedInfo(
  val remoteId: String,
  val type: Int,
  val title: String,
  val relatedData: List<String>,
  val relatedItems: List<RelatedItem>,
)
