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
import com.kuuy.taoniu.data.images.api.ImageApi
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ImagesModule {
  @Provides
  @Singleton
  fun provideImageApi(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): ImageApi {
    return getDynamicRetrofitAuthClient(
      okHttpClient,
      gsonConverterFactory,
    ).create(ImageApi::class.java)
  }

  private fun getDynamicRetrofitAuthClient(
    @Named(NetworkModule.AUTH_HTTP_CLIENT) client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory,
  ): Retrofit {
    return Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.IMAGES_API_URL)
      .addConverterFactory(gsonConverterFactory)
      .build()
  }
}
