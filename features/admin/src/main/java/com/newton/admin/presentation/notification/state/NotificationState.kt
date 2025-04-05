package com.newton.admin.presentation.notification.state

import java.time.*

data class NotificationState(
    val subject: String = "",
    val message: String = "",
    val linkTitle: String = "",
    val linkUrl: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val uploadSuccess: Boolean = false,
    val successUpload: Boolean = false,
    val showLinkDialog: Boolean = false,
    val isScheduled: Boolean = false,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val scheduledDateTime: LocalDateTime? = null,
    val selectedDate: LocalDateTime = LocalDateTime.now()
)
