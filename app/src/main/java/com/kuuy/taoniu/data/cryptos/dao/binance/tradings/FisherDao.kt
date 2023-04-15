package com.kuuy.taoniu.data.cryptos.dao.binance.tradings

import com.kuuy.taoniu.data.DbResult
import com.kuuy.taoniu.data.cryptos.entities.binance.tradings.FisherEntity
import com.kuuy.taoniu.data.groceries.AppDatabase
import com.kuuy.taoniu.data.groceries.entities.CounterOrderEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.max
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FisherDao @Inject constructor() {
  val realm: Realm by lazy { AppDatabase().realm }

  fun scan(market: String): Flow<DbResult<Array<String>>> {
    return flow {
      val fishers = realm.query<FisherEntity>(
        "market == $0",
        market,
      ).first().find()
      fishers?.run {
        emit(
          DbResult.Success(fishers.symbols.toTypedArray())
        )
      }
    }.flowOn(Dispatchers.Main)
  }

  fun addAll(scene: String, symbols: Array<String>): Flow<DbResult<Nothing?>> {
    return flow {
      realm.writeBlocking {
        val fishers = realm.query<FisherEntity>("scene == $0", scene)
          .first()
          .find()
        fishers?.run {
          realm.writeBlocking {
            fishers.symbols.clear()
            fishers.symbols.addAll(symbols)
          }
        } ?: {
          val id = this.query<FisherEntity>()
            .max<Long>("id")
            .find() ?: 0L
          copyToRealm(
            FisherEntity().apply {
              this.id = id + 1L
              this.scene = scene
              this.symbols.addAll(symbols)
            }
          )
        }
      }
      emit(DbResult.Success(null))
    }.flowOn(Dispatchers.Main)
  }
}