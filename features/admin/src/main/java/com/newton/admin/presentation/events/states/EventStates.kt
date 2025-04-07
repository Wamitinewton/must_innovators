package com.newton.admin.presentation.events.states

import java.io.File
import java.time.LocalDateTime

data class AddEventState(
    val name: String = "",
    val category: String = "",
    val description: String = "",
    val image: File?= null,
    val date: Long = System.currentTimeMillis(),
    val location: String = "",
    val organizer: String = "",
    val contactEmail: String = "",
    val title: String = "",
    val meetingLink : String = "",
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
    val uploadSuccess: Boolean = false,
)

sealed class AddEventEffect {
    data object PickImage : AddEventEffect()
}
