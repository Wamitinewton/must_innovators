package com.newton.core.domain.models.event_models

import java.io.File

data class AddEventRequest (
    val name: String,
    val category:String,
    val description:String,
    val image:File,
    val date: String,
    val location: String,
    val organizer:String,
    val contactEmail:String,
    val title:String,
    val isVirtual:Boolean,
)
