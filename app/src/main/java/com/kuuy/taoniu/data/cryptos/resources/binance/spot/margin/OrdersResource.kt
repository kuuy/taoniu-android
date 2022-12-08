package com.kuuy.taoniu.data.cryptos.resources.binance.spot.margin

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.OrdersApi
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.OrderInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OrdersResource @Inject constructor(
  private var ordersApi: OrdersApi
) {
  suspend fun listings(
    symbols: List<String>,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResponse<DtoPaginate<OrderInfoDto>>> {
    return flow {
      val response = ordersApi.listings(symbols.joinToString(","), current, pageSize)
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}