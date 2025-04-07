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

import com.newton.network.domain.models.adminModels.EventsData

sealed class EventUpdateEvent {
    data class Update(val eventId: Int) : EventUpdateEvent()
    data class NameChanged(val name: String) : EventUpdateEvent()
    data class CategoryChanged(val category: String) : EventUpdateEvent()
    data class DescriptionChanged(val description: String) : EventUpdateEvent()
    data class LocationChanged(val location: String) : EventUpdateEvent()
    data class OrganizerChanged(val organizer: String) : EventUpdateEvent()
    data class ContactEmailChanged(val email: String) : EventUpdateEvent()
    data class VirtualChanged(val virtual: Boolean) : EventUpdateEvent()
    data object ToDefault : EventUpdateEvent()
}

sealed class UpdateEvent {
    data object Initial : UpdateEvent()
    data class Success(val event: EventsData) : UpdateEvent()
    data class Error(val message: String) : UpdateEvent()
}
