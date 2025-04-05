package com.newton.notifications.data.remote

import retrofit2.*
import retrofit2.http.*

interface NotificationApiService {
    @POST("users/register-device")
    suspend fun registerDevice(
        @Body request: DeviceRegistrationRequest
    ): Response<DeviceRegistrationResponse>
}

data class DeviceRegistrationRequest(
    val userId: String,
    val deviceToken: String,
    val platform: String = "android"
)

data class DeviceRegistrationResponse(
    val success: Boolean,
    val message: String?
)
