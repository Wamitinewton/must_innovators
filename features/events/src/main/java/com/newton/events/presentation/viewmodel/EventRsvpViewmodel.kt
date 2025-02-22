package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.utils.InputValidators
import com.newton.core.utils.Resource
import com.newton.core.utils.ValidationResult
import com.newton.events.domain.models.EventRegistrationRequest
import com.newton.events.domain.repository.EventRepository
import com.newton.events.presentation.events.RsvpEvent
import com.newton.events.presentation.states.EventRegistrationFormState
import com.newton.events.presentation.states.RegistrationState
import com.newton.events.presentation.states.RegistrationValidationResult
import com.newton.events.presentation.states.RegistrationValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventRsvpViewmodel @Inject constructor(
    private val repository: EventRepository
): ViewModel() {
    // Channel for success event
    private val _rsvpEvents = Channel<RsvpEvent>()
    val rsvpEvent = _rsvpEvents.receiveAsFlow()

    // UI State for reg
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val registrationState: StateFlow<RegistrationState> = _registrationState

    /**
     * Form state for collecting reg data
     */
    private val _formState = MutableStateFlow(EventRegistrationFormState())
    val formState: StateFlow<EventRegistrationFormState> = _formState

    // Field validation states
    private val _validationState = MutableStateFlow(RegistrationValidationState())
    val validationState: StateFlow<RegistrationValidationState> = _validationState

    //Submit registration request

    fun registerForEvent(eventId: Int) {
        val validationResults = validateAllFields()

        if (!validationResults.isFormValid) {
            return
        }

        val request = EventRegistrationRequest(
            full_name = "${_formState.value.firstName} ${_formState.value.lastName}",
            email = _formState.value.email,
            course = _formState.value.course,
            educational_level = _formState.value.educationLevel,
            phone_number = _formState.value.phoneNumber,
            expectations = _formState.value.expectations
        )

        viewModelScope.launch {
            repository.registerForEvent(eventId, request).onEach { result ->
                when(result) {
                    is Resource.Error -> {
                        _registrationState.value = RegistrationState.Error(
                            result.message ?: "An Error occuured"
                        )
                        _rsvpEvents.send(RsvpEvent.ShowError)
                    }
                    is Resource.Loading -> {
                        _registrationState.value = RegistrationState.Loading(result.isLoading)
                    }
                    is Resource.Success -> {
                        result.data?.let { response ->
                            _registrationState.value = RegistrationState.Success(response)

                            _rsvpEvents.send(RsvpEvent.ShowSuccessBottomSheet(response))
                            resetForm()
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    fun updateFirstName(firstName: String) {
        _formState.value = _formState.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String) {
        _formState.value = _formState.value.copy(lastName = lastName)
    }

    fun updateEmail(email: String) {
        _formState.value = _formState.value.copy(email = email)
    }

    fun updatePhoneNumber(phoneNumber: String) {
        if (phoneNumber.all { it.isDigit() } || phoneNumber.isEmpty()) {
            _formState.value = _formState.value.copy(phoneNumber = phoneNumber)
            validatePhoneNumber()
        }
    }

    fun updateCourse(course: String) {
        _formState.value = _formState.value.copy(course = course)
    }

    fun updateEducationLevel(level: String) {
        _formState.value = _formState.value.copy(educationLevel = level)
    }

    fun updateExpectations(expectations: String) {
        _formState.value = _formState.value.copy(expectations = expectations)
    }

    private fun validateExpectations(): ValidationResult {
        val expectations = _formState.value.expectations
        val result = when {
            expectations.isBlank() -> ValidationResult(false, "Please share your expectations")
            expectations.length < 10 -> ValidationResult(false, "Please provide more details about your expectations")
            else -> ValidationResult(true)
        }

        _validationState.value = _validationState.value.copy(
            expectationsError = if (!result.isValid) result.errorMessage else null
        )
        return result
    }

    private fun validatePhoneNumber(): ValidationResult {
        val phoneNumber = _formState.value.phoneNumber
        val result = when {
            phoneNumber.isBlank() -> ValidationResult(false, "Phone number is needed")
            phoneNumber.length < 10 -> ValidationResult(false, "Phone number must be at least 10 digits")
            !phoneNumber.all { it.isDigit() } -> ValidationResult(false, "Phone number must contain only digits")
            else -> ValidationResult(true)
        }
        _validationState.value = _validationState.value.copy(
            phoneNumberError = if (!result.isValid) result.errorMessage else null
        )
        return result
    }

    private fun validateAllFields(): RegistrationValidationResult {
        val phoneValidation = validatePhoneNumber()
        val expectationsValidation = validateExpectations()

        val isFormValid = phoneValidation.isValid &&
                expectationsValidation.isValid &&
                _formState.value.firstName.isNotBlank() &&
                _formState.value.lastName.isNotBlank() &&
                _formState.value.course.isNotBlank() &&
                _formState.value.educationLevel.isNotBlank()

        return RegistrationValidationResult(isFormValid)
    }

    /**
     * In case of successful registration, we want to clear form data
     * Before navigating users to event details screen
     */
    private fun resetForm() {
        _formState.value = EventRegistrationFormState(
            firstName = _formState.value.firstName,
            lastName = _formState.value.lastName,
            email = _formState.value.email,
            course = _formState.value.course,
            phoneNumber = "",
            educationLevel = "1",
            expectations = ""
        )

        _validationState.value = RegistrationValidationState()
    }
}