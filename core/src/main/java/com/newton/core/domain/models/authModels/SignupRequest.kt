package com.newton.core.domain.models.authModels

import kotlinx.serialization.*

@Serializable
data class SignupRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val username: String,
    val course: String? = null
)
