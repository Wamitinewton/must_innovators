package com.newton.core.domain.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationResponse<T>(
    @SerialName("count")
    val count: Int = 0,
    @SerialName("next")
    val next: String = "",
    @SerialName("previous")
    val previous: String = "",
    @SerialName("results")
    val results: List<T> = listOf()
)