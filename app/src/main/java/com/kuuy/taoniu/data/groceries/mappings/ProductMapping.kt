package com.kuuy.taoniu.data.groceries.mappings

import com.kuuy.taoniu.data.groceries.dto.ProductListingsDto
import com.kuuy.taoniu.data.groceries.dto.ProductInfoDto
import com.kuuy.taoniu.data.groceries.dto.ProductDetailDto
import com.kuuy.taoniu.data.groceries.dto.ProductBarcodeDto
import com.kuuy.taoniu.data.groceries.models.ProductListings
import com.kuuy.taoniu.data.groceries.models.ProductInfo
import com.kuuy.taoniu.data.groceries.models.ProductDetail
import com.kuuy.taoniu.data.groceries.models.ProductBarcode


fun ProductListingsDto.transform(): ProductListings {
  return ProductListings(
    products=products.transform(),
  )
}

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
    barcode,
    title,
    intro,
    price,
    cover,
  )
}

fun ProductBarcodeDto.transform(): ProductBarcode {
  return ProductBarcode(
    barcode,
    productId,
  )
}
