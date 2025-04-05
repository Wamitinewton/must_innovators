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
