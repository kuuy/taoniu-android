package com.kuuy.taoniu.data.cryptos.resources.binance.spot.margin.isolated.tradings.fishers

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
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
  private var gridsApi: GridsApi
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
        var apiError = ApiError(
          response.code(),
          response.message(),
        )
        try {
          response.errorBody()?.let {
            apiError = Gson().fromJson(it.charStream(), ApiError::class.java)
          }
        } catch (ioException: JsonIOException) {
        } catch (syntaxException: JsonSyntaxException) {
        }
        emit(ApiResponse.Error(apiError))
      }
    }.catch {}.flowOn(Dispatchers.IO)
  }
}