package com.kuuy.taoniu.data.groceries.mappings

import com.kuuy.taoniu.data.groceries.dto.ProductInfoDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.models.ProductInfo
import com.kuuy.taoniu.data.groceries.models.ProductDetail

fun List<ProductInfoDto>.transform(): List<ProductInfo> {
  return this.map { it.transform() }
}

fun ProductInfoDto.transform(): ProductInfo {
  return ProductInfo(
    remoteId=id,
    title,
    intro,
    price,
    cover,
  )
}

fun ProductDetailDto.transform(): ProductDetail {
  return ProductDetail(
    remoteId=id,
    title,
    intro,
    price,
    cover,
  )
}

