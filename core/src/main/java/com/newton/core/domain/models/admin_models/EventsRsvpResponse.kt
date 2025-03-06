package com.newton.core.domain.models.admin_models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsRsvpResponse(
    @SerialName("data")
    val `data`: Data = Data(),
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = ""
)