package com.newton.admin.presentation.notification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.notification.events.NotificationEvent
import com.newton.admin.presentation.notification.state.NotificationState
import com.newton.core.domain.models.admin.NewsLetter
import com.newton.core.domain.repositories.AdminRepository
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationState())
    val notificationState: StateFlow<NotificationState> = _notificationState.asStateFlow()

    fun handleEvents(event: NotificationEvent) {
        when (event) {
            is NotificationEvent.IsSuccessfullySent -> _notificationState.update { it.copy(successUpload = event.success) }
            is NotificationEvent.LoadingStateChange -> _notificationState.update { it.copy(isLoading = event.isLoading) }
            is NotificationEvent.MessageChange -> _notificationState.update { it.copy(message = event.message) }
            is NotificationEvent.SubjectChange -> _notificationState.update { it.copy(subject = event.subject) }
            is NotificationEvent.ScheduledChanged -> _notificationState.update { it.copy(isScheduled = event.scheduled) }
            is NotificationEvent.ShowLinkDialog -> _notificationState.update { it.copy(showLinkDialog= event.shown) }
            is NotificationEvent.LinkChange -> _notificationState.update { it.copy(linkUrl = event.link) }
            is NotificationEvent.LinkTitleChange -> _notificationState.update { it.copy(linkTitle = event.title) }
            is NotificationEvent.ScheduledDateTimeChanged -> _notificationState.update { it.copy(scheduledDateTime = event.date) }
            is NotificationEvent.SelectedDateChange -> _notificationState.update { it.copy(selectedDate = event.date) }
            is NotificationEvent.ShowDateDialog -> _notificationState.update { it.copy(showDatePicker = event.shown) }
            is NotificationEvent.ShowTimeDialog -> _notificationState.update { it.copy(showTimePicker = event.shown) }
            NotificationEvent.SendNewsLetter -> sendNewsletter()
            NotificationEvent.ToDefault -> toDefault()
        }
    }

    private fun toDefault(){
        _notificationState.value = NotificationState()
    }
    private fun sendNewsletter() {
        val newsLetter = NewsLetter(
            subject = _notificationState.value.subject,
            message = _notificationState.value.message
        )
        viewModelScope.launch {
            adminRepository.sendNewsLetter(newsLetter).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _notificationState.value = _notificationState.value.copy(
                            errorMessage = result.message
                                ?: "Unknown error occured while sending newsletter"
                        )
                    }

                    is Resource.Loading -> {
                        _notificationState.value =
                            _notificationState.value.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _notificationState.value = _notificationState.value.copy(
                            successUpload = true,
                            isLoading = false,
                            errorMessage = null,
                        )
                    }
                }
            }
        }
    }
}