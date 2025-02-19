package com.newton.events.domain.models

import com.newton.core.domain.models.event_models.EventsData

data class ScoredEventData(
    val eventsData: EventsData,
    val score: Double
)
