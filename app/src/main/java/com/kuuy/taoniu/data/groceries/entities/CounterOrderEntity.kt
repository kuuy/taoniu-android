package com.kuuy.taoniu.data.groceries.entities

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.*

open class CounterOrderEntity : RealmObject {
  @PrimaryKey
  var id: Long = 0
  var productId: String = ""
  var title: String = ""
  var price: Float = 0.0f
  var discount: Float = 0.0f
  var discountPrice: Float = 0.0f
  var reducePrice: Float = 0.0f
  var quantity: Int = 0
}
