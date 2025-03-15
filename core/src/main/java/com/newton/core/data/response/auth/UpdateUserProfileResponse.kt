package com.newton.core.data.response.auth

import com.newton.core.domain.models.auth_models.UserData
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileResponse(
    val message: String,
    val status: String,
    val data: UserData
)