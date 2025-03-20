package com.newton.events.presentation.states

import com.newton.core.domain.models.admin_models.RegistrationResponse
import com.newton.core.enums.EventRegistrationFlow

data class EventRegistrationState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val course: String = "",
    val educationLevel: String = "1",
    val expectations: String = "",
    val phoneNumberError: String? = null,
    val expectationsError: String? = null,
    val educationLevelError: String? = null,
    val isLoading: Boolean = false,
    val success: RegistrationResponse? = null,
    val errorMessage: String? = null,
    val eventRegistrationFlow: EventRegistrationFlow = EventRegistrationFlow.REGISTER
)
