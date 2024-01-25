package com.kuuy.taoniu.data

import android.content.SharedPreferences
import android.view.View
import com.kuuy.taoniu.data.account.repositories.TokenRepository
import com.kuuy.taoniu.di.PreferencesModule
import kotlinx.coroutines.flow.catch
import okhttp3.*
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthToken constructor(
  @Named(PreferencesModule.AUTH_PREFERENCES) private var authPreferences: SharedPreferences,
  private var tokenRepository: TokenRepository,
) {
  fun accessToken(): String? {
    return authPreferences.getString(ACCESS_TOKEN, null)
  }

  fun shouldRefresh(): Boolean {
    val now = System.currentTimeMillis()
    val refreshAt = authPreferences.getLong("REFRESH_AT", 0L)
    if (now > refreshAt) {
      return true
    }
    return false
  }

  suspend fun refresh() {
    val refreshToken = authPreferences.getString("REFRESH_TOKEN", null)

    if (refreshToken != null) {
      tokenRepository.refresh(refreshToken).catch {
        it.message?.let { message ->
          Timber.tag(TAG).d("refresh token call failed. $message")
        }
      }.collect { response ->
        when (response) {
          is ApiResource.Loading -> {}
          is ApiResource.Success -> {
            response.data?.let {
              authPreferences.edit()
                .putString("ACCESS_TOKEN", it.access)
                .putLong("REFRESH_AT", System.currentTimeMillis() + 895000)
                .apply()
            }
          }
          is ApiResource.Error -> {
            response.apiError?.let {
              if (it.code == 401 || it.code == 403) {
                clear()
              }
            }
          }
        }
      }
    }
  }

  fun clear() {
    authPreferences.edit()
      .clear()
      .remove(ACCESS_TOKEN)
      .remove(REFRESH_TOKEN)
      .remove(REFRESH_AT)
      .apply()
  }

  companion object {
    private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    private const val REFRESH_AT = "REFRESH_AT"
    private const val TAG = "AUTH_TOKEN"
  }
}
