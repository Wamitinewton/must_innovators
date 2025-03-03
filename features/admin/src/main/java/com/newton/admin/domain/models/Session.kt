package com.newton.admin.domain.models

import com.newton.admin.domain.models.enums.SessionType

data class Session(
    val title: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val sessionType: String
)