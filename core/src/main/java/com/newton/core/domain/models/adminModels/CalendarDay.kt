package com.newton.core.domain.models.adminModels

import java.time.*

data class CalendarDay(
    val date: LocalDate,
    val events: List<EventsData>
)
