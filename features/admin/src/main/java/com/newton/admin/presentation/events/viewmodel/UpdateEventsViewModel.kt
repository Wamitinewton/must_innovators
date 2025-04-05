package com.newton.admin.presentation.events.viewmodel

import androidx.lifecycle.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.states.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class UpdateEventsViewModel
@Inject
constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {
    private val _updateState = MutableStateFlow(UpdateEventState())
    val updateState: StateFlow<UpdateEventState> = _updateState.asStateFlow()

    fun handleEvents(event: EventUpdateEvent) {
        when (event) {
            is EventUpdateEvent.CategoryChanged -> _updateState.update { it.copy(category = event.category) }
            is EventUpdateEvent.ContactEmailChanged -> _updateState.update { it.copy(contactEmail = event.email) }
            is EventUpdateEvent.DescriptionChanged ->
                _updateState.update {
                    it.copy(
                        description = event.description
                    )
                }

            is EventUpdateEvent.LocationChanged -> _updateState.update { it.copy(location = event.location) }
            is EventUpdateEvent.NameChanged -> _updateState.update { it.copy(name = event.name) }
            is EventUpdateEvent.OrganizerChanged -> _updateState.update { it.copy(organizer = event.organizer) }
            is EventUpdateEvent.Update -> updateEvent(event.eventId)
            is EventUpdateEvent.VirtualChanged -> _updateState.update { it.copy(isVirtual = event.virtual) }
        }
    }

    private fun updateEvent(eventId: Int) {
        val request =
            UpdateEventRequest(
                name = _updateState.value.name,
                category = _updateState.value.category,
                description = _updateState.value.description,
                image = _updateState.value.image,
                date = _updateState.value.image,
                location = _updateState.value.location,
                organizer = _updateState.value.organizer,
                contactEmail = _updateState.value.contactEmail,
                title = _updateState.value.title,
                meetingLink = _updateState.value.meetingLink,
                isVirtual = _updateState.value.isVirtual
            )

        viewModelScope.launch {
            adminRepository.updateEvent(request, eventId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _updateState.value = _updateState.value.copy(errorMessage = result.message)
                    }

                    is Resource.Loading -> {
                        _updateState.value = _updateState.value.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _updateState.value =
                            _updateState.value.copy(
                                uploadSuccess = true,
                                isLoading = false,
                                errorMessage = null
                            )
                    }
                }
            }
        }
    }
}
