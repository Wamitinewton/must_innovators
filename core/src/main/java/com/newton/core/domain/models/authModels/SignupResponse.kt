package com.newton.core.domain.models.authModels

import kotlinx.serialization.*

@Serializable
data class SignupResponse(
    val message: String,
    val status: String
)
