package com.newton.notifications.data.repository

import android.content.Context
import android.os.Build
import com.newton.database.dao.UserDao
import com.newton.notifications.data.remote.DeviceRegistrationRequest
import com.newton.notifications.data.remote.NotificationApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FCMTokenRepository @Inject constructor(
    private val context: Context,
    private val apiService: NotificationApiService,
    private val userDao: UserDao
) {
    companion object {
        private const val TAG = "FCMTokenRepository"
        private const val PREFS_NAME = "notification_prefs"
        private const val KEY_FCM_TOKEN = "fcm_token"
    }

    /**
     * Save FCM token to local shared prefs
     */

    suspend fun saveToken(token: String) = withContext(Dispatchers.IO) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString(KEY_FCM_TOKEN, token)
            .apply()
    }

    /**
     * Get the stored FCM token
     */
    suspend fun getToken(): String? = withContext(Dispatchers.IO) {
        return@withContext context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_FCM_TOKEN, null)
    }

    suspend fun sendTokenToBackend(token: String): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val storedToken = getToken()

            /**
             * Avoid sending the same token repeatedly
             */
            if (storedToken == token) {
                return@withContext Result.success(true)
            }

            val response = apiService.registerDevice(
                request = DeviceRegistrationRequest(
                    userId = getUserId(),
                    deviceToken = token,
                    platform = Build.DEVICE
                )
            )
            return@withContext if (response.isSuccessful) {
                saveToken(token)
                Result.success(true)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("Failed to register device: $errorMsg"))
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    private suspend fun getUserId(): String {
        val id = userDao.getUser()?.id.toString()
        return id
    }
}