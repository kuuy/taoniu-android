package com.kuuy.taoniu.data.groceries.mappings

import com.kuuy.taoniu.data.groceries.dto.FeedInfoDto
import com.kuuy.taoniu.data.groceries.models.FeedInfo

fun List<FeedInfoDto>.transform(): List<FeedInfo> {
  return this.map { it.transform() }
}
fun FeedInfoDto.transform(): FeedInfo {
  return FeedInfo(
    remoteId=id,
    type,
    title,
    relatedData,
    relatedItems.map { it.transform() }
  )
}