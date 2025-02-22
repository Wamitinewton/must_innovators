package com.newton.events.presentation.events

import com.newton.core.domain.models.event_models.RegistrationResponse

sealed class RsvpEvent {
    data class ShowSuccessBottomSheet(val response: RegistrationResponse): RsvpEvent()
    data object ShowError: RsvpEvent()
}