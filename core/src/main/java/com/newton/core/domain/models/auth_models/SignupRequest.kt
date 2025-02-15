package com.newton.core.domain.models.auth_models

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val username: String,
    val course: String? = null,
)
