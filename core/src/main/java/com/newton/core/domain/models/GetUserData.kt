package com.newton.core.domain.models
import kotlinx.serialization.Serializable

@Serializable
data class GetUserData(
    val data: UserData,
    val message: String,
    val status: String
)

@Serializable
data class UserData(
    val course: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val username: String,
    val id: Int? = null
)