package com.newton.events.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("data")
    val `data`: Data = Data(),
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = ""
)