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
import javax.inject.Named
import com.kuuy.taoniu.data.cryptos.api.currencies.AboutApi as CurrenciesAboutApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.SymbolsApi as BinanceSpotSymbolsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.TradingsApi as BinanceSpotTradingsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.TickersApi as BinanceSpotTickersApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.KlinesApi as BinanceSpotKlinesApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.plans.DailyApi as BinanceSpotPlansDailyApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.OrdersApi as BinanceSpotMarginOrdersApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.SymbolsApi as BinanceSpotMarginIsolatedSymbolsApi
import com.kuuy.taoniu.data.cryptos.api.binance.spot.margin.isolated.TradingsApi as BinanceSpotMarginIsolatedTradingsApi
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotSymbolsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotSymbolsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotTradingsApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotTradingsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotTradingsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotTickersApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotMarginIsolatedSymbolsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotMarginIsolatedSymbolsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotMarginIsolatedTradingsApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): BinanceSpotMarginIsolatedTradingsApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(BinanceSpotMarginIsolatedTradingsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideBinanceSpotMarginIsolatedTradingsGridsApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): TradingviewAnalysisApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(TradingviewAnalysisApi::class.java)
  }

  private fun getDynamicRetrofitClient(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.CRYPTOS_API_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }
}
