package com.newton.core.domain.models.admin_models

import com.newton.core.domain.models.event_models.EventsData
import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val events: List<EventsData>
)
