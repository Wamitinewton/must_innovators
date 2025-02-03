package com.newton.auth.domain.models.sign_up

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val userName: String,
    val password: String,
    val registration_no: String? = null,
    val course: String? = null,
)
