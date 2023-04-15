package com.kuuy.taoniu.data.cryptos.entities.binance.spot

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey

open class TickerEntity : RealmObject {
  @PrimaryKey
  var id: Long = 0
  @Index
  var symbol: String = ""
  var open: Float = 0F
  var price: Float = 0F
  var high: Float = 0F
  var low: Float = 0F
  var volume: Float = 0F
  var quota: Float = 0F
  var state: Int = 0
  var change: Float = 0F
  var changeState: Int = 0
}