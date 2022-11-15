package com.kuuy.taoniu.di

import com.kuuy.taoniu.BuildConfig
import com.kuuy.taoniu.data.account.api.AuthApi
import com.kuuy.taoniu.data.account.api.TokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
  @Provides
  @Singleton
  fun providrAuthApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): AuthApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(AuthApi::class.java)
  }

  @Provides
  @Singleton
  fun provideTokenApi(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): TokenApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(TokenApi::class.java)
  }

  private fun getDynamicRetrofitClient(
    client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.ACCOUNT_API_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }
}