package com.newton.core.domain.models.adminModels

import java.io.*

data class AddEventRequest(
    val name: String,
    val category: String,
    val description: String,
    val image: File,
    val date: String,
    val location: String,
    val organizer: String,
    val contactEmail: String,
    val title: String,
    val isVirtual: Boolean
)
