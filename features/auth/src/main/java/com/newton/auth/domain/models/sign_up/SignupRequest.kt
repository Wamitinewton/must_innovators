package com.newton.auth.domain.models.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val course: String? = null,
)
