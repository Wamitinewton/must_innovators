package com.newton.auth.domain.models.sign_up

data class UserDataResponse(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val nonFieldErrors: List<String>? = null

)
