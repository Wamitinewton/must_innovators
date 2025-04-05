package com.newton.core.domain.models.authModels

import kotlinx.serialization.*

@Serializable
data class ResetPasswordRequest(
    val new_password: String,
    val email: String
)
