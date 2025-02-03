package com.newton.auth.domain.models.sign_up

data class SignupResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val nonFieldErrors: List<String>? = null

)
