package com.newton.events.domain.models

data class EventRequest(
    val contactEmail: String,
    val date: String,
    val description: String,
    val image: String,
    val isVirtual: Boolean,
    val location: String,
    val name: String,
    val organizer: String,
    val title: String
)
