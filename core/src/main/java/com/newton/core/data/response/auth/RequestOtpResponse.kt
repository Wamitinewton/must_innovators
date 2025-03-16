package com.newton.core.data.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class RequestOtpResponse(
    val message: String
)


@Serializable
data class OtpVerificationResponse(
    val message: String,
)
