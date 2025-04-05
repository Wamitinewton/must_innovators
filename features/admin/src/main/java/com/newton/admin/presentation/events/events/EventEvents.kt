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
package com.newton.admin.presentation.events.events

import com.newton.core.domain.models.adminModels.*

sealed class EventEvents {
    data class SelectedEvent(val event: EventsData?) : EventEvents()

    data class EditingEvent(val editing: Boolean) : EventEvents()

    data class GetEventsAttendees(val eventId: Int) : EventEvents()

    data class GetEventFeedbacks(val eventId: Int, val isRefresh: Boolean) : EventEvents()

    data object LoadEvents : EventEvents()
}
