package com.newton.core.domain.models

import kotlinx.serialization.*

data class ApiResponse<T>(
    @SerialName("data")
    val `data`: T,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = ""
)
