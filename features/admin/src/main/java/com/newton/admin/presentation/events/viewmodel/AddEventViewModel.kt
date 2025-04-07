package com.newton.admin.presentation.events.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.admin.presentation.events.states.AddEventEffect
import com.newton.admin.presentation.events.states.AddEventState
import com.newton.common_ui.ui.toLocalDateTime
import com.newton.core.domain.models.admin_models.AddEventRequest
import com.newton.core.domain.repositories.AdminRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val repository: AdminRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddEventState())
    val state: StateFlow<AddEventState> = _state.asStateFlow()


    val uiSideEffect get() = _uiSideEffect.asSharedFlow()
    private val _uiSideEffect = MutableSharedFlow<AddEventEffect>()

    val categories = listOf(
        "WEB",
        "CYBERSEC",
        "ANDROID",
        "AI",
        "BLOCKCHAIN",
        "IoT"
    )

    fun handleEvent(event: AddEventEvents) {
        when (event) {
            is AddEventEvents.ChangedCategory -> _state.update { it.copy(category = event.category) }
            is AddEventEvents.ChangeDate -> _state.update { it.copy(date = event.date) }
            is AddEventEvents.ChangedLocation -> _state.update { it.copy(location = event.location) }
            is AddEventEvents.ChangedOrganizer -> _state.update { it.copy(organizer = event.organizer) }
            is AddEventEvents.Sheet -> _state.update { it.copy(showCategorySheet = event.shown) }
            is AddEventEvents.ChangedFile -> _state.update { it.copy(image = event.file) }
            is AddEventEvents.ChangedMeetingLink -> _state.update { it.copy(meetingLink = event.link) }
            is AddEventEvents.ChangedContactEmail -> _state.update { it.copy(contactEmail = event.email) }
            is AddEventEvents.ChangedDescription -> _state.update { it.copy(description = event.description) }
            is AddEventEvents.ChangedName -> _state.update { it.copy(name = event.name) }
            is AddEventEvents.ChangedTitle -> _state.update { it.copy(title = event.title) }
            is AddEventEvents.ChangedVirtual -> _state.update { it.copy(isVirtual = event.isVirtual) }
            is AddEventEvents.ScheduledDateTimeChanged -> _state.update { it.copy(scheduledDateTime = event.date) }
            is AddEventEvents.SelectedDateChange -> _state.update { it.copy(selectedDate = event.date) }
            is AddEventEvents.ShowDateDialog -> _state.update { it.copy(showDatePicker = event.shown) }
            is AddEventEvents.ShowTimeDialog -> _state.update { it.copy(showTimePicker = event.shown) }
            AddEventEvents.ToDefaultSate -> toDefaultState()
            AddEventEvents.AddEvent -> saveEvent()
            AddEventEvents.PickImage -> emit(AddEventEffect.PickImage)
            AddEventEvents.ClearImage -> _state.update { it.copy(image = null) }

        }
    }


    private fun saveEvent() {
        if (validateAndSubmit()) {
            val request = AddEventRequest(
                name = _state.value.name,
                category = _state.value.category,
                description = _state.value.description,
                image = _state.value.image!!,
                date = _state.value.selectedDate.toLocalDateTime(),
                location = _state.value.location,
                organizer = _state.value.organizer,
                contactEmail = _state.value.contactEmail,
                title = _state.value.title,
                isVirtual = _state.value.isVirtual
            )
            viewModelScope.launch {
                repository.addEvent(request).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(uploadError = result.message)
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = result.isLoading)
                        }

                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                uploadSuccess = true,
                                isLoading = false,
                                uploadError = null,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun emit(effect: AddEventEffect) = viewModelScope.launch {
        _uiSideEffect.emit(effect)
    }

    private fun toDefaultState() {
        _state.value = AddEventState()
    }

    private fun validateAndSubmit(): Boolean {
        val errors = mutableMapOf<String, String>()
        if (_state.value.name.isBlank()) {
            errors["name"] = "Event name is required"
        }
        if (_state.value.category.isBlank()) {
            errors["category"] = "Category is required"
        }
        if (_state.value.image == null) {
            errors["image"] = "Event poster is required"
        }
        if (_state.value.organizer.isBlank()) {
            errors["organizer"] = "Organizer name is required"
        }

        if (_state.value.isVirtual && _state.value.meetingLink.isBlank()) {
            errors["meetingLink"] = "Meeting link  is required"
        }

        if (_state.value.contactEmail.isBlank()) {
            errors["email"] = "Contact email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_state.value.contactEmail).matches()) {
            errors["email"] = "Invalid email format"
        }

        if (_state.value.title.isBlank()) {
            errors["title"] = "Title is required"
        }

        _state.value = _state.value.copy(errors = errors)
        return errors.isEmpty()
    }


}