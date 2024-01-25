package com.kuuy.taoniu.data.groceries.mappings

import com.kuuy.taoniu.data.groceries.dto.BarcodeInfoDto
import com.kuuy.taoniu.data.groceries.models.BarcodeInfo

fun BarcodeInfoDto.transform(): BarcodeInfo {
  return BarcodeInfo(
    remoteId = id,
    barcode,
    productId,
  )
}