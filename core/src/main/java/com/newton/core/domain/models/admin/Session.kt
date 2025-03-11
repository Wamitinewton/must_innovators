package com.newton.core.domain.models.admin

data class Session(
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val sessionType: String
)