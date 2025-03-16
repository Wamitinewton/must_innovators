package com.newton.core.data.response.events

import kotlinx.serialization.Serializable

@Serializable
data class EventApiResponse<T>(
    val message: String,
    val status: String,
    val data: T
)

@Serializable
data class EventResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<EventDto>
)

@Serializable
data class EventDto(
    val id: Int,
    val image_url: String,
    val name: String,
    val category: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val organizer: String,
    val contact_email: String,
    val is_virtual: Boolean,

)
