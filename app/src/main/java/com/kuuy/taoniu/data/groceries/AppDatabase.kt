package com.kuuy.taoniu.data.groceries

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

import android.content.Context

import com.kuuy.taoniu.data.groceries.entities.CounterOrderEntity

class AppDatabase() {
  val realm: Realm by lazy {
    val config = RealmConfiguration.Builder(
      schema = setOf(
        CounterOrderEntity::class,
      ),
    ).name("groceries.realm")
      .deleteRealmIfMigrationNeeded()
      .build()
    Realm.open(config)
  }
}
