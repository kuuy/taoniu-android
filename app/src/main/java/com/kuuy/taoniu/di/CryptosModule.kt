package com.kuuy.taoniu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.cryptos.api.OrderApi
import com.kuuy.taoniu.data.cryptos.api.StrategyApi
import com.kuuy.taoniu.data.cryptos.api.currencies.AboutApi as CurrenciesAboutApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.SymbolsApi as BinanceSpotSymbolsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.tradings.SymbolsApi as BinanceSpotTradingsSymbolsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.TickersApi as BinanceSpotTickersApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.KlinesApi as BinanceSpotKlinesApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.plans.DailyApi as BinanceSpotPlansDailyApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.OrdersApi as BinanceSpotMarginOrdersApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.SymbolsApi as BinanceSpotMarginIsolatedSymbolsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.tradings.SymbolsApi as BinanceSpotMarginIsolatedTradingsSymbolsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.tradings.GridsApi as BinanceSpotMarginIsolatedTradingsGridsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.analysis.tradings.fishers.GridsApi as BinanceSpotAnalysisTradingsFishersGridsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.analysis.margin.isolated.tradings.fishers.GridsApi as BinanceSpotAnalysisMarginIsolatedTradingsFishersGridsApi
import com.kuuy.taoniu.data.cryptos.api.tradingview.AnalysisApi as TradingviewAnalysisApi

@Module
@InstallIn(SingletonComponent::class)
object CryptosModule {
  @Provides
  @Singleton
  fun provideOrderApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): OrderApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(OrderApi::class.java)
  }

  @Provides
  @Singleton
  fun provideStrategyApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): StrategyApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(StrategyApi::class.java)
  }

  @Provides
  @Singleton
  fun provideCurrenciesAboutApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): CurrenciesAboutApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(CurrenciesAboutApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotSymbolsApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotSymbolsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotSymbolsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotTradingsSymbolsApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotTradingsSymbolsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotTradingsSymbolsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotTickersApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotTickersApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotTickersApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotKlinesApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotKlinesApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotKlinesApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotPlansDailyApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotPlansDailyApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotPlansDailyApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotMarginOrdersApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotMarginOrdersApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotMarginOrdersApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotMarginIsolatedSymbolsApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotMarginIsolatedSymbolsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotMarginIsolatedSymbolsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotMarginIsolatedTradingsSymbolsApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotMarginIsolatedTradingsSymbolsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotMarginIsolatedTradingsSymbolsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotMarginIsolatedTradingsGridsApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotMarginIsolatedTradingsGridsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotMarginIsolatedTradingsGridsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotAnalysisTradingsFishersGridsApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotAnalysisTradingsFishersGridsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotAnalysisTradingsFishersGridsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotAnalysisMarginIsolatedTradingsFishersGridsApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotAnalysisMarginIsolatedTradingsFishersGridsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotAnalysisMarginIsolatedTradingsFishersGridsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideTradingviewAnalysisApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): TradingviewAnalysisApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(TradingviewAnalysisApi::class.java)
  }

  private fun getDynamicRetrofitClient(
    client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.CRYPTOS_API_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }
}
