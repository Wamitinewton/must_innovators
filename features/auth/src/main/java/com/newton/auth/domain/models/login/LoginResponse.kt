package com.newton.auth.domain.models.login

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
