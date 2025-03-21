package com.newton.events.presentation.events

import com.newton.auth.presentation.sign_up.event.SignupUiEvent


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
    data object ClearError: EventRsvpUiEvent

}

