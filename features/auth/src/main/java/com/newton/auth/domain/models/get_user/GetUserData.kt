package com.newton.auth.domain.models.get_user

import kotlinx.serialization.Serializable

@Serializable
data class GetUserData(
    val data: Data,
    val message: String,
    val status: String
)

@Serializable
data class Data(
    val course: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val username: String
)