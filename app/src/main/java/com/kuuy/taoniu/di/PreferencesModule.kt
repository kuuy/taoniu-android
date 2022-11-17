package com.kuuy.taoniu.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {
  @Provides
  @Singleton
  @Named(SETTINGS_PREFERENCES)
  fun provideSettingsPreferences(context: Context): SharedPreferences =
      context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE)

  @Provides
  @Singleton
  @Named(AUTH_PREFERENCES)
  fun provideAuthPreferences(context: Context): SharedPreferences =
      context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)

  companion object {
    const val AUTH_PREFERENCES = "auth_preferences"
    const val SETTINGS_PREFERENCES = "settings_preferences"
  }
}
