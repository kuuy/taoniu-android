package com.kuuy.taoniu.di

import android.content.SharedPreferences
import com.kuuy.taoniu.data.ApiInterceptor
import com.kuuy.taoniu.data.account.repositories.TokenRepository
import com.kuuy.taoniu.data.network.ApiService
import com.kuuy.taoniu.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Singleton
  @Provides
  fun providesHttpClient(
    @Named(PreferencesModule.AUTH_PREFERENCES) authPreferences: SharedPreferences
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(ApiInterceptor(authPreferences))
      .readTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(20, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .build()
  }

  @Singleton
  @Provides
  fun providesGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
  }

  @Singleton
  @Provides
  fun providesRetrofitInstance(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }

  @Singleton
  @Provides
  fun providesApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

}
