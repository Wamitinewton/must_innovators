package com.newton.auth.domain.models.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val message: String,
    val status: String,
)