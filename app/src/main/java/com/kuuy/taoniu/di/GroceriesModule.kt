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
import com.kuuy.taoniu.data.groceries.api.ProductApi
import com.kuuy.taoniu.data.groceries.resources.ProductResource

@Module
@InstallIn(SingletonComponent::class)
object GroceriesModule {
  @Provides
  @Singleton
  fun provideProductApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): ProductApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(ProductApi::class.java)
  }

  private fun getDynamicRetrofitClient(
    client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.GROCERIES_API_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }
}
