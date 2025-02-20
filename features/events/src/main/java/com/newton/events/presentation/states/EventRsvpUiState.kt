package com.newton.events.presentation.states

import com.newton.core.domain.models.event_models.RegistrationResponse

data class EventRegistrationFormState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val course: String = "",
    val educationLevel: String = "1",
    val expectations: String = ""
)

data class RegistrationValidationState(
    val phoneNumberError: String? = null,
    val expectationsError: String? = null
)

data class RegistrationValidationResult(
    val isFormValid: Boolean
)

sealed class RegistrationState {
    data object Initial : RegistrationState()
    data class Loading(val isLoading: Boolean) : RegistrationState()
    data class Success(val data: RegistrationResponse) : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}