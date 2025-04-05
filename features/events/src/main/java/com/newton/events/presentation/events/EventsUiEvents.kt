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
package com.newton.events.presentation.events

/**
 * UI Events for EventRsvp screen
 */
sealed interface EventRsvpUiEvent {
    data class UpdateFirstName(val firstName: String) : EventRsvpUiEvent

    data class UpdateLastName(val lastName: String) : EventRsvpUiEvent

    data class UpdateEmail(val email: String) : EventRsvpUiEvent

    data class UpdatePhoneNumber(val phoneNumber: String) : EventRsvpUiEvent

    data class UpdateCourse(val course: String) : EventRsvpUiEvent

    data class UpdateEducationLevel(val level: String) : EventRsvpUiEvent

    data class UpdateExpectations(val expectations: String) : EventRsvpUiEvent

    data class SubmitRegistration(val eventId: Int) : EventRsvpUiEvent

    data object ClearError : EventRsvpUiEvent
}
