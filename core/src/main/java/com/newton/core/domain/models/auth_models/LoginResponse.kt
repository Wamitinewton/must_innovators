package com.newton.core.domain.models.auth_models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val data: LoginResultData
)

@Serializable
data class LoginResultData(
    val refresh: String,
    val access: String
)
