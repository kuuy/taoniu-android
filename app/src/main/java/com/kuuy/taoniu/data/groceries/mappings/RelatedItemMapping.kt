package com.kuuy.taoniu.data.groceries.mappings

import com.kuuy.taoniu.data.groceries.dto.RelatedItemDto
import com.kuuy.taoniu.data.groceries.models.RelatedItem

fun List<RelatedItemDto>.transform(): List<RelatedItem> {
  return this.map { it.transform() }
}

fun RelatedItemDto.transform(): RelatedItem {
  return RelatedItem(
    remoteId=id,
    resourceId,
  )
}