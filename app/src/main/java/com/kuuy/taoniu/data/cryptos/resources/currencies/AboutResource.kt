package com.kuuy.taoniu.data.cryptos.resources.currencies

import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoResponse
import com.kuuy.taoniu.data.cryptos.api.currencies.AboutApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AboutResource @Inject constructor(
  private var aboutApi: AboutApi
) {
  suspend fun get(
    symbol: String,
  ): Flow<ApiResponse<DtoResponse<String>>> {
    return flow {
      val response = aboutApi.get(symbol)
      emit(ApiResponse.Success(response))
    }.catch {}.flowOn(Dispatchers.IO)
  }
}