package com.kuuy.taoniu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import android.content.Context

import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.groceries.api.ProductApi
import com.kuuy.taoniu.data.groceries.resources.ProductResource
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object GroceriesModule {
  @Provides
  @Singleton
  fun provideProductApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): ProductApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(ProductApi::class.java)
  }

  private fun getDynamicRetrofitClient(
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
