package com.newton.core.domain.models.authModels

import kotlinx.serialization.*

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
