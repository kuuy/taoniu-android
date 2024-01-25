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
import com.kuuy.taoniu.data.groceries.api.FeedsApi
import com.kuuy.taoniu.data.groceries.api.ProductsApi
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object GroceriesModule {
  @Provides
  @Singleton
  fun provideFeedsApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): FeedsApi {
    return getDynamicRetrofitAuthClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(FeedsApi::class.java)
  }

  @Provides
  @Singleton
  fun provideProductApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): ProductsApi {
    return getDynamicRetrofitAuthClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(ProductsApi::class.java)
  }

  private fun getDynamicRetrofitAuthClient(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.GROCERIES_API_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }
}
