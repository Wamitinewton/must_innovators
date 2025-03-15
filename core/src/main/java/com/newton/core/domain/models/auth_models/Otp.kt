package com.newton.core.domain.models.auth_models

import kotlinx.serialization.Serializable

@Serializable
data class OtpRequest(
    val email: String
)

@Serializable
data class VerifyOtp(
    val otp_code: String,
    val email: String
)
