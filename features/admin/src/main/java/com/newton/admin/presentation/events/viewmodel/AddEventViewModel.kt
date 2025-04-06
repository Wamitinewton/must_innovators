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
package com.newton.admin.presentation.events.viewmodel

import android.util.*
import androidx.lifecycle.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.states.*
import com.newton.commonUi.ui.*
import com.newton.network.*
import com.newton.network.domain.models.adminModels.*
import com.newton.network.domain.repositories.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*
import kotlin.collections.set

@HiltViewModel
class AddEventViewModel
@Inject
constructor(
    private val repository: AdminRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddEventState())
    val state: StateFlow<AddEventState> = _state.asStateFlow()

    val uiSideEffect get() = _uiSideEffect.asSharedFlow()
    private val _uiSideEffect = MutableSharedFlow<AddEventEffect>()

    val categories =
        listOf(
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
            is AddEventEvents.Dialog -> _state.update { it.copy(isShowDialog = event.shown) }
            is AddEventEvents.Sheet -> _state.update { it.copy(showCategorySheet = event.shown) }
            is AddEventEvents.ChangedFile -> _state.update { it.copy(image = event.file) }
            is AddEventEvents.ChangedMeetingLink -> _state.update { it.copy(meetingLink = event.link) }
            is AddEventEvents.ChangedContactEmail -> _state.update { it.copy(contactEmail = event.email) }
            is AddEventEvents.ChangedDescription -> _state.update { it.copy(description = event.description) }
            is AddEventEvents.ChangedName -> _state.update { it.copy(name = event.name) }
            is AddEventEvents.ChangedTitle -> _state.update { it.copy(title = event.title) }
            is AddEventEvents.ChangedVirtual -> _state.update { it.copy(isVirtual = event.isVirtual) }
            AddEventEvents.ToDefaultSate -> toDefaultState()
            AddEventEvents.AddEvent -> saveEvent()
            AddEventEvents.PickImage -> emit(AddEventEffect.PickImage)
            AddEventEvents.ClearImage -> _state.update { it.copy(image = null) }
        }
    }

    private fun saveEvent() {
        if (validateAndSubmit()) {
            val request =
                AddEventRequest(
                    name = _state.value.name,
                    category = _state.value.category,
                    description = _state.value.description,
                    image = _state.value.image!!,
                    date = _state.value.date.toLocaltime(),
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
                            _state.value =
                                _state.value.copy(
                                    uploadSuccess = true,
                                    isLoading = false,
                                    uploadError = null
                                )
                        }
                    }
                }
            }
        }
    }

    private fun emit(effect: AddEventEffect) =
        viewModelScope.launch {
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
