package com.kuuy.taoniu.data.groceries.dao

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.*

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import com.kuuy.taoniu.data.DbResult
import com.kuuy.taoniu.data.groceries.AppDatabase
import com.kuuy.taoniu.data.groceries.entities.CounterOrderEntity
import com.kuuy.taoniu.data.groceries.models.CounterOrderListings
import com.kuuy.taoniu.data.groceries.models.CounterOrderInfo
import com.kuuy.taoniu.data.groceries.models.CounterOrderDetail

class CounterOrderDao @Inject constructor(
  private @ApplicationContext val context: Context
) {

  val realm: Realm by lazy { AppDatabase().realm }

  fun getOrderListings(): Flow<DbResult<CounterOrderListings>> {
    return flow {
      val listings: MutableList<CounterOrderInfo> = mutableListOf()
      val orders = realm.query<CounterOrderEntity>()
          .sort("id", Sort.ASCENDING)
          .find()
      for (order in orders) {
        listings.add(CounterOrderInfo(
          order.id,
          order.productId,
          order.title,
          order.price,
          order.discount,
          order.discountPrice,
          order.reducePrice,
          order.quantity,
        ))
      }
      emit(DbResult.Success(CounterOrderListings(
        listings,
      )))
    }.flowOn(Dispatchers.Main)
  }

  fun getOrderDetail(id: Long): Flow<DbResult<CounterOrderDetail>> {
    return flow {
      val order = realm.query<CounterOrderEntity>(
        "id == $0",
        id
      ).first().find()
      order?.run {
        emit(DbResult.Success(CounterOrderDetail(
          order.id,
          order.productId,
          order.title,
          order.price,
          order.discount,
          order.discountPrice,
          order.reducePrice,
          order.quantity,
        )))
      }
    }.flowOn(Dispatchers.Main)
  }

  fun insert(
    productId: String,
    title: String,
    price: Float,
    discount: Float,
    discountPrice: Float,
    reducePrice: Float,
    quantity: Int
  ) : Flow<DbResult<Nothing?>> {
    return flow {
      realm.writeBlocking {
        val id = this.query<CounterOrderEntity>()
            .max<Long>("id")
            .find() ?: 0L
        copyToRealm(
          CounterOrderEntity().apply {
            this.id = id + 1L
            this.productId = productId
            this.title = title
            this.price = price
            this.discount = discount
            this.discountPrice = discountPrice
            this.reducePrice = reducePrice
            this.quantity = quantity
          }
        )
      }
      emit(DbResult.Success(null))
    }.flowOn(Dispatchers.Main)
  }

  fun update(
    id: Long,
    price: Float,
    discount: Float,
    discountPrice: Float,
    reducePrice: Float,
    quantity: Int
  ) : Flow<DbResult<Nothing?>> {
    return flow {
      val order = realm.query<CounterOrderEntity>("id == $0", id)
          .first()
          .find()
      order?.run {
        realm.writeBlocking {
          order.price = price
          order.discount = discount
          order.discountPrice = discountPrice
          order.reducePrice = reducePrice
          order.quantity = quantity
        }
      }
      emit(DbResult.Success(null))
    }.flowOn(Dispatchers.Main)
  }

  fun delete(id: Long) : Flow<DbResult<Nothing?>> {
    return flow {
      realm.writeBlocking {
        val query = this.query<CounterOrderEntity>(
          "id == $0",
          id
        ).first()
        delete(query)
      }
      emit(DbResult.Success(null))
    }.flowOn(Dispatchers.Main)
  }

  fun clear() : Flow<DbResult<Nothing?>> {
    return flow {
      realm.writeBlocking {
        val query = this.query<CounterOrderEntity>()
        delete(query)
      }
      emit(DbResult.Success(null))
    }.flowOn(Dispatchers.Main)
  }

  fun addProduct(
    productId: String,
    title: String,
    price: Float
  ) : Flow<DbResult<Nothing?>> {
    return flow {
      val order = realm.query<CounterOrderEntity>(
        "productId == $0",
        productId
      ).first().find()
      if (order == null) {
        realm.writeBlocking {
          val id = this.query<CounterOrderEntity>()
              .max<Long>("id")
              .find() ?: 0L
          copyToRealm(
            CounterOrderEntity().apply {
              this.id = id + 1
              this.productId = productId
              this.title = title
              this.price = price
              this.discount = 0.0f
              this.discountPrice = 0.0f
              this.reducePrice = 0.0f
              this.quantity = 1
            }
          )
        }
      } else {
        realm.writeBlocking {
          findLatest(order)?.quantity = order.quantity + 1
        }
      }
      emit(DbResult.Success(null))
    }.flowOn(Dispatchers.Main)
  }
}
