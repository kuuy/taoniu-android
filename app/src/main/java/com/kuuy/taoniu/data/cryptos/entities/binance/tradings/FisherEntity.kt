package com.kuuy.taoniu.data.cryptos.entities.binance.tradings

import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.*

open class FisherEntity : RealmObject {
  @PrimaryKey
  var id: Long = 0
  @Index
  var scene: String = ""
  var symbols: RealmSet<String> = realmSetOf()
}