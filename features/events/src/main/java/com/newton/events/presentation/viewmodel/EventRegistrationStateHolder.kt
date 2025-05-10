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
package com.newton.events.presentation.viewmodel

import com.newton.core.enums.*
import com.newton.events.presentation.events.*
import com.newton.events.presentation.states.*
import com.newton.network.data.dto.admin.*
import com.newton.network.domain.models.eventModels.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class EventRegistrationStateHolder
@Inject
constructor(
    private val eventRegistrationValidator: EventRegistrationValidator
) {
    private val _registrationState = MutableStateFlow(EventRegistrationState())
    val registrationState: StateFlow<EventRegistrationState> = _registrationState.asStateFlow()

    fun updateState(event: EventRsvpUiEvent) {
        when (event) {
            is EventRsvpUiEvent.UpdateFirstName -> firstNameChanged(event.firstName)
            is EventRsvpUiEvent.UpdateLastName -> lastNameChanged(event.lastName)
            is EventRsvpUiEvent.UpdateEmail -> emailChanged(event.email)
            is EventRsvpUiEvent.UpdatePhoneNumber -> phoneNumberChanged(event.phoneNumber)
            is EventRsvpUiEvent.UpdateCourse -> courseChanged(event.course)
            is EventRsvpUiEvent.UpdateEducationLevel -> educationLevelChanged(event.level)
            is EventRsvpUiEvent.UpdateExpectations -> expectationsChanged(event.expectations)
            is EventRsvpUiEvent.ClearError -> clearErrors()
            else -> {}
        }
    }

    private fun clearErrors() {
        _registrationState.update {
            it.copy(
                expectationsError = null,
                phoneNumberError = null,
                errorMessage = null,
                educationLevelError = null
            )
        }
    }

    private fun firstNameChanged(firstName: String) {
        _registrationState.update {
            it.copy(
                firstName = firstName
            )
        }
    }

    private fun lastNameChanged(lastName: String) {
        _registrationState.update {
            it.copy(
                lastName = lastName
            )
        }
    }

    private fun emailChanged(email: String) {
        _registrationState.update {
            it.copy(
                email = email
            )
        }
    }

    private fun phoneNumberChanged(phoneNumber: String) {
        val validationResult = eventRegistrationValidator.validatePhoneNumber(phoneNumber)
        _registrationState.update {
            it.copy(
                phoneNumber = phoneNumber,
                phoneNumberError = validationResult.errorMessage
            )
        }
    }

    private fun courseChanged(course: String) {
        _registrationState.update {
            it.copy(
                course = course
            )
        }
    }

    private fun educationLevelChanged(year: String) {
        val validationResult = eventRegistrationValidator.validateEducationLevel(year)
        _registrationState.update {
            it.copy(
                educationLevel = year,
                educationLevelError = validationResult.errorMessage
            )
        }
    }

    private fun expectationsChanged(expectations: String) {
        val validationResult = eventRegistrationValidator.validateExpectations(expectations)
        _registrationState.update {
            it.copy(
                expectations = expectations,
                expectationsError = validationResult.errorMessage
            )
        }
    }

    fun isFormValid(): Boolean {
        val phoneValidation =
            eventRegistrationValidator.validatePhoneNumber(_registrationState.value.phoneNumber)
        val educationLevelValidation =
            eventRegistrationValidator.validateEducationLevel(_registrationState.value.educationLevel)
        val expectationsValidation =
            eventRegistrationValidator.validateExpectations(_registrationState.value.expectations)

        _registrationState.update { currentState ->
            currentState.copy(
                phoneNumberError = phoneValidation.errorMessage,
                educationLevelError = educationLevelValidation.errorMessage,
                expectationsError = expectationsValidation.errorMessage
            )
        }

        return phoneValidation.isValid &&
            educationLevelValidation.isValid &&
            expectationsValidation.isValid
    }

    fun setLoading(isLoading: Boolean) {
        _registrationState.update { it.copy(isLoading = isLoading) }
    }

    fun setError(message: String?) {
        _registrationState.update { it.copy(errorMessage = message) }
    }

    fun setSuccess(
        message: RegistrationResponse?,
        eventRegistrationFlow: EventRegistrationFlow
    ) {
        _registrationState.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                success = message,
                eventRegistrationFlow = eventRegistrationFlow
            )
        }
    }

    fun getEventRegistrationRequest(): EventRegistrationRequest {
        return EventRegistrationRequest(
            full_name = "${_registrationState.value.firstName} ${_registrationState.value.lastName}",
            email = _registrationState.value.email,
            course = _registrationState.value.course,
            educational_level = _registrationState.value.educationLevel,
            phone_number = _registrationState.value.phoneNumber,
            expectations = _registrationState.value.expectations
        )
    }
}
