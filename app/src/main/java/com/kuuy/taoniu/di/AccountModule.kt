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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {
  @Provides
  @Singleton
  fun provideAuthApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
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
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): TokenApi {
    return getDynamicRetrofitClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(TokenApi::class.java)
  }

  private fun getDynamicRetrofitClient(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.ACCOUNT_API_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }
}