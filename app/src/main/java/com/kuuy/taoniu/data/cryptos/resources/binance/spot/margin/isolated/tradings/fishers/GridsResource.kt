package com.kuuy.taoniu.data.cryptos.resources.binance.spot.margin.isolated.tradings.fishers

import com.google.gson.Gson
import com.kuuy.taoniu.data.ApiError
import com.kuuy.taoniu.data.ApiResponse
import com.kuuy.taoniu.data.DtoPaginate
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.tradings.GridsApi
import com.kuuy.taoniu.data.cryptos.dto.binance.spot.margin.isolated.tradings.GridInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GridsResource @Inject constructor(
  private var gridsApi: GridsApi,
  private var gson: Gson,
) {
  suspend fun listings(
    symbols: List<String>,
    current: Int,
    pageSize: Int,
  ) : Flow<ApiResponse<DtoPaginate<GridInfoDto>>> {
    return flow {
      val response = gridsApi.listings(symbols.joinToString(","), current, pageSize)
      if (response.isSuccessful) {
        response.body()?.let {
          emit(ApiResponse.Success(it))
        }
      } else {
        response.errorBody()?.let {
          var apiError = gson.fromJson(it.charStream(), ApiError::class.java)
          emit(ApiResponse.Error(apiError))
        }
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }
}