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
package com.newton.admin.presentation.notification.events

import java.time.*

sealed class NotificationEvent {
    data object SendNewsLetter : NotificationEvent()

    data object ToDefault : NotificationEvent()

    data class SubjectChange(val subject: String) : NotificationEvent()

    data class MessageChange(val message: String) : NotificationEvent()

    data class LoadingStateChange(val isLoading: Boolean) : NotificationEvent()

    data class IsSuccessfullySent(val success: Boolean) : NotificationEvent()

    data class ShowLinkDialog(val shown: Boolean) : NotificationEvent()

    data class ShowDateDialog(val shown: Boolean) : NotificationEvent()

    data class ShowTimeDialog(val shown: Boolean) : NotificationEvent()

    data class ScheduledChanged(val scheduled: Boolean) : NotificationEvent()

    data class LinkChange(val link: String) : NotificationEvent()

    data class LinkTitleChange(val title: String) : NotificationEvent()

    data class SelectedDateChange(val date: LocalDateTime) : NotificationEvent()

    data class ScheduledDateTimeChanged(val date: LocalDateTime) : NotificationEvent()
}
