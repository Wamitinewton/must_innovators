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
package com.newton.admin.presentation.notification.viewmodel

import androidx.lifecycle.*
import com.newton.admin.presentation.notification.events.*
import com.newton.admin.presentation.notification.state.*
import com.newton.core.domain.models.admin.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class NotificationsViewModel
@Inject
constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {
    private val _notificationState = MutableStateFlow(NotificationState())
    val notificationState: StateFlow<NotificationState> = _notificationState.asStateFlow()

    fun handleEvents(event: NotificationEvent) {
        when (event) {
            is NotificationEvent.IsSuccessfullySent ->
                _notificationState.update {
                    it.copy(
                        successUpload = event.success
                    )
                }

            is NotificationEvent.LoadingStateChange ->
                _notificationState.update {
                    it.copy(
                        isLoading = event.isLoading
                    )
                }

            is NotificationEvent.MessageChange -> _notificationState.update { it.copy(message = event.message) }
            is NotificationEvent.SubjectChange -> _notificationState.update { it.copy(subject = event.subject) }
            is NotificationEvent.ScheduledChanged ->
                _notificationState.update {
                    it.copy(
                        isScheduled = event.scheduled
                    )
                }

            is NotificationEvent.ShowLinkDialog ->
                _notificationState.update {
                    it.copy(
                        showLinkDialog = event.shown
                    )
                }

            is NotificationEvent.LinkChange -> _notificationState.update { it.copy(linkUrl = event.link) }
            is NotificationEvent.LinkTitleChange -> _notificationState.update { it.copy(linkTitle = event.title) }
            is NotificationEvent.ScheduledDateTimeChanged ->
                _notificationState.update {
                    it.copy(
                        scheduledDateTime = event.date
                    )
                }

            is NotificationEvent.SelectedDateChange ->
                _notificationState.update {
                    it.copy(
                        selectedDate = event.date
                    )
                }

            is NotificationEvent.ShowDateDialog ->
                _notificationState.update {
                    it.copy(
                        showDatePicker = event.shown
                    )
                }

            is NotificationEvent.ShowTimeDialog ->
                _notificationState.update {
                    it.copy(
                        showTimePicker = event.shown
                    )
                }

            NotificationEvent.SendNewsLetter -> sendNewsletter()
            NotificationEvent.ToDefault -> toDefault()
        }
    }

    private fun toDefault() {
        _notificationState.value = NotificationState()
    }

    private fun sendNewsletter() {
        val newsLetter =
            NewsLetter(
                subject = _notificationState.value.subject,
                message = _notificationState.value.message
            )
        viewModelScope.launch {
            adminRepository.sendNewsLetter(newsLetter).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _notificationState.value =
                            _notificationState.value.copy(
                                errorMessage =
                                result.message
                                    ?: "Unknown error occured while sending newsletter"
                            )
                    }

                    is Resource.Loading -> {
                        _notificationState.value =
                            _notificationState.value.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _notificationState.value =
                            _notificationState.value.copy(
                                successUpload = true,
                                isLoading = false,
                                errorMessage = null
                            )
                    }
                }
            }
        }
    }
}
