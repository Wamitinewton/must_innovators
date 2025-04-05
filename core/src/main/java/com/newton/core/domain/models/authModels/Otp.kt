package com.newton.core.domain.models.authModels

import kotlinx.serialization.*

@Serializable
data class OtpRequest(
    val email: String
)

@Serializable
data class VerifyOtp(
    val otp_code: String,
    val email: String
)
