package com.newton.core.domain.models.event_models

import kotlinx.serialization.Serializable

@Serializable
data class EventsData(
    val id: Int,
    val imageUrl: String? = "",
    val name: String,
    val category: String,
    val description: String,
    val date: String,
    val location: String,
    val organizer: String,
    val contactEmail: String,
    val isVirtual: Boolean
)
