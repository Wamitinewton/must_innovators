package com.newton.auth.data.remote.authResponse.sign_up

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupResponseDto(
    @SerialName("data")
    val data: SignupDto,
    @SerialName("message")
    val message: String
)
