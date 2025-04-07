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
package com.newton.admin.presentation.events.states

import java.io.File
import java.time.LocalDateTime

data class AddEventState(
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val image: File? = null,
    val date: Long = System.currentTimeMillis(),
    val location: String = "",
    val organizer: String = "",
    val contactEmail: String = "",
    val title: String = "",
    val meetingLink: String = "",
    val isVirtual: Boolean = false,
    val showCategorySheet: Boolean = false,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val scheduledDateTime: LocalDateTime = LocalDateTime.now(),
    val selectedDate: LocalDateTime = LocalDateTime.now(),
    val errors: Map<String, String> = emptyMap(),
    val isShowDialog: Boolean = false,
    val uploadError: String? = null,
    val isLoading: Boolean = false,
    val uploadSuccess: Boolean = false
)

sealed class AddEventEffect {
    data object PickImage : AddEventEffect()
}
