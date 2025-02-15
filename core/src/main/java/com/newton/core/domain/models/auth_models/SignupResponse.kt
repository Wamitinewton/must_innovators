package com.newton.core.domain.models.auth_models

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val message: String,
    val status: String,
)