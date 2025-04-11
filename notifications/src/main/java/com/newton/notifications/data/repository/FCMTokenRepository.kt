/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.notifications.data.repository

import android.content.*
import android.os.*
import com.newton.database.dao.*
import com.newton.notifications.data.remote.*
import kotlinx.coroutines.*
import javax.inject.*

class FCMTokenRepository
@Inject
constructor(
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

    suspend fun saveToken(token: String) =
        withContext(Dispatchers.IO) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                .putString(KEY_FCM_TOKEN, token)
                .apply()
        }

    /**
     * Get the stored FCM token
     */
    suspend fun getToken(): String? =
        withContext(Dispatchers.IO) {
            return@withContext context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_FCM_TOKEN, null)
        }

    suspend fun sendTokenToBackend(token: String): Result<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val storedToken = getToken()

                /**
                 * Avoid sending the same token repeatedly
                 */
                if (storedToken == token) {
                    return@withContext Result.success(true)
                }

                val response =
                    apiService.registerDevice(
                        request =
                            DeviceRegistrationRequest(
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
