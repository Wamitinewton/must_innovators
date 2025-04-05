package com.newton.core.domain.models.aboutUs

import kotlinx.serialization.*

@Serializable
data class ExecutiveData(
    val data: List<Executive>,
    val message: String,
    val status: String
)

@Serializable
data class Executive(
    val bio: String?,
    val email: String,
    val id: Int,
    val name: String,
    val position: String
)
