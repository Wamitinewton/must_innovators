package com.newton.core.domain.models.admin

data class Session(
    val day: String,
    val start_time: String,
    val end_time: String,
    val location: String,
    val meeting_type: String
)