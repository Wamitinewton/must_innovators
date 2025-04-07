/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.database.mappers

import com.newton.database.entities.*
import com.newton.network.domain.models.adminModels.*

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
