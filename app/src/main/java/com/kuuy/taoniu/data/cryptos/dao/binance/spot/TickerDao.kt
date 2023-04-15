package com.kuuy.taoniu.data.cryptos.dao.binance.spot

import com.kuuy.taoniu.data.DbResult
import com.kuuy.taoniu.data.cryptos.entities.binance.spot.TickerEntity
import com.kuuy.taoniu.data.cryptos.models.Ticker
import com.kuuy.taoniu.data.groceries.AppDatabase
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.max
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TickerDao @Inject constructor() {
  val realm: Realm by lazy { AppDatabase().realm }

  fun save(symbol: String, ticker: Ticker): Flow<DbResult<Nothing?>> {
    return flow {
      realm.writeBlocking {
        val entity = realm.query<TickerEntity>("symbol == $0", symbol)
          .first()
          .find()
        entity?.run {
          realm.writeBlocking {
            entity.open = ticker.open
            entity.price = ticker.price
            entity.high = ticker.high
            entity.low = ticker.low
            entity.volume = ticker.volume
            entity.quota = ticker.quota
            entity.state = ticker.state
            entity.change = ticker.change
            entity.changeState = ticker.changeState
          }
        } ?: {
          val id = this.query<TickerEntity>()
            .max<Long>("id")
            .find() ?: 0L
          copyToRealm(
            TickerEntity().apply {
              this.id = id + 1L
              this.symbol = symbol
              this.price = ticker.price
              this.high = ticker.high
              this.low = ticker.low
              this.volume = ticker.volume
              this.quota = ticker.quota
              this.state = ticker.state
              this.change = ticker.change
              this.changeState = ticker.changeState
            }
          )
        }
      }
      emit(DbResult.Success(null))
    }.flowOn(Dispatchers.Main)
  }
}