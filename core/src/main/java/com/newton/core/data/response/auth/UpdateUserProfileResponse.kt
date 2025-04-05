package com.newton.core.data.response.auth

import com.newton.core.domain.models.authModels.*
import kotlinx.serialization.*

@Serializable
data class UpdateUserProfileResponse(
    val message: String,
    val status: String,
    val data: UserData
)
