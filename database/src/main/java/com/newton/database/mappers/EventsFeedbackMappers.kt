package com.newton.database.mappers

import com.newton.core.domain.models.adminModels.*
import com.newton.database.entities.*

fun EventsFeedback.toEventFeedbackEntity(): EventsFeedbackEntity {
    return EventsFeedbackEntity(
        id = id,
        attendeeId = attendeeId,
        rating = rating,
        comment = comment,
        submittedAt = submittedAt,
        attendeeName = attendeeName
    )
}

fun EventsFeedbackEntity.toDomain(): EventsFeedback {
    return EventsFeedback(
        id,
        attendeeId,
        rating,
        comment,
        submittedAt,
        attendeeName
    )
}

fun List<EventsFeedback>.toEventsFeedbackList(): List<EventsFeedbackEntity> =
    map { it.toEventFeedbackEntity() }
