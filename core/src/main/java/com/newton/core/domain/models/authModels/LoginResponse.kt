package com.newton.core.domain.models.authModels

import kotlinx.serialization.*

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
