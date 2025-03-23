package com.newton.core.domain.models.admin_models

import java.io.File

data class UpdateEventRequest(
    val name: String? = null,
    val category: String? = null,
    val description: String? = null,
    val image: File?= null,
    val date: File? = null,
    val location: String? = null,
    val organizer: String? = null,
    val contactEmail: String? = null,
    val title: String? = null,
    val meetingLink : String? = null,
    val isVirtual: Boolean? = null,
)
