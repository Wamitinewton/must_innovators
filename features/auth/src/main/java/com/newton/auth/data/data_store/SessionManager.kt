package com.newton.auth.data.data_store

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.newton.auth.data.data_store.DataStoreKeys.KEY_ACCESS_TOKEN
import com.newton.auth.data.data_store.DataStoreKeys.KEY_REFRESH_TOKEN
import com.newton.auth.data.data_store.DataStoreKeys.PREF_FILE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePreferences = EncryptedSharedPreferences.create(
        context,
        PREF_FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveAccessToken(token: String) {
        securePreferences.edit().apply {
            putString(KEY_ACCESS_TOKEN, token)
            apply()
        }
    }

    fun fetchAccessToken(): String? {
        return securePreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun saveRefreshToken(token: String) {
        securePreferences.edit().apply {
            putString(KEY_REFRESH_TOKEN, token)
            apply()
        }
    }

    fun fetchRefreshToken(): String? {
        return securePreferences.getString(KEY_REFRESH_TOKEN, null)
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        securePreferences.edit().apply {
            putString(KEY_ACCESS_TOKEN, accessToken)
            putString(KEY_REFRESH_TOKEN, refreshToken)
            apply()
        }
    }

    fun updateTokens(accessToken: String?, refreshToken: String?) {
        securePreferences.edit().apply {
            accessToken?.let { putString(KEY_ACCESS_TOKEN, it) }
            refreshToken?.let { putString(KEY_REFRESH_TOKEN, it) }
            apply()
        }
    }

    fun clearTokens() {
        securePreferences.edit().clear().apply()
    }


}