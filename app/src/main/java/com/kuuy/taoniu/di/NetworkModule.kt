package com.kuuy.taoniu.di

import android.content.SharedPreferences
import com.kuuy.taoniu.data.ApiInterceptor
import com.kuuy.taoniu.data.AuthInterceptor
import com.kuuy.taoniu.data.AuthToken
import com.kuuy.taoniu.data.account.repositories.TokenRepository
import com.kuuy.taoniu.data.network.ApiService
import com.kuuy.taoniu.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  const val HTTP_CLIENT = "http_client"
  const val AUTH_HTTP_CLIENT = "auth_http_client"

  @Provides
  @Singleton
  fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

  @Singleton
  @Provides
  @Named(HTTP_CLIENT)
  fun provideHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .readTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(20, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .build()
  }

  @Singleton
  @Provides
  @Named(AUTH_HTTP_CLIENT)
  fun provideAuthHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authToken: AuthToken,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(ApiInterceptor(authToken))
      .addInterceptor(AuthInterceptor(authToken))
      .addNetworkInterceptor(loggingInterceptor)
      .readTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(20, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .build()
  }

  @Singleton
  @Provides
  fun provideGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
  }

  @Singleton
  @Provides
  fun provideRetrofitInstance(
    @Named(HTTP_CLIENT) client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }

  @Singleton
  @Provides
  fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

  @Singleton
  @Provides
  fun provideAuthToken(
    @Named(PreferencesModule.AUTH_PREFERENCES) authPreferences: SharedPreferences,
    tokenRepository: TokenRepository,
  ): AuthToken {
    return AuthToken(authPreferences, tokenRepository)
  }
}
