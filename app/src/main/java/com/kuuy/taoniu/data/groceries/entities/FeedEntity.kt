package com.kuuy.taoniu.data.groceries.entities

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class FeedEntity : RealmObject {
  @PrimaryKey
  var id: Long = 0
}