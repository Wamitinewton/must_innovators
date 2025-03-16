package com.newton.core.domain.models.auth_models

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val password: String,
    val email: String,
)