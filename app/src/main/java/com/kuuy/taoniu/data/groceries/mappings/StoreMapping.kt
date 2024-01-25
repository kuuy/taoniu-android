package com.kuuy.taoniu.data.groceries.mappings

import com.kuuy.taoniu.data.groceries.dto.StoreInfoDto
import com.kuuy.taoniu.data.groceries.dto.StoreDetailDto
import com.kuuy.taoniu.data.groceries.models.StoreInfo
import com.kuuy.taoniu.data.groceries.models.StoreDetail

fun List<StoreInfoDto>.transform(): List<StoreInfo> {
  return this.map { it.transform() }
}

fun StoreInfoDto.transform(): StoreInfo {
  return StoreInfo(
    remoteId=id,
    name,
    logo,
  )
}

fun StoreDetailDto.transform(): StoreDetail {
  return StoreDetail(
    remoteId=id,
    name,
    logo,
  )
}

