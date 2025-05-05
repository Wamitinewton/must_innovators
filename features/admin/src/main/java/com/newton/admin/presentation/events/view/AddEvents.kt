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
package com.newton.admin.presentation.events.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.commonUi.ui.*
import java.time.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvents(
    viewModel: AddEventViewModel,
    onEvent: (AddEventEvents) -> Unit
) {
    val state by viewModel.state.collectAsState()


    CustomScaffold(
        topBar = {
            MeruInnovatorsAppBar(
                title = "Add Event"
            )
        },
        isLoading = state.isLoading
    ) {
        when {
            state.uploadSuccess -> {
                AdminSuccessScreen(
                    onFinish = {
                        onEvent.invoke(AddEventEvents.ToDefaultSate)
                    },
                    text = "Event created Successfully"
                )
            }

            else -> {
                AddEventContent(state, onEvent, viewModel)
            }
        }
        if (state.showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = System.currentTimeMillis(),
                selectableDates = object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        val currentDate = LocalDateTime.now().toLocalDate().atStartOfDay()
                        val currentMillis = currentDate.toInstant(ZoneOffset.UTC).toEpochMilli()
                        return utcTimeMillis >= currentMillis
                    }
                }
            )
            DatePickerDialog(
                onDismissRequest = { onEvent.invoke(AddEventEvents.ShowDateDialog(shown = false)) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { dateMillis ->
                                val date = Instant.ofEpochMilli(dateMillis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime()
                                onEvent.invoke(AddEventEvents.SelectedDateChange(date))
                                onEvent.invoke(AddEventEvents.ShowDateDialog(false))
                                onEvent.invoke(AddEventEvents.ShowTimeDialog(true))
                            }
                        }
                    ) {
                        Text("Next")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { onEvent.invoke(AddEventEvents.ShowDateDialog(false)) }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (state.showTimePicker) {
            val timePickerState = rememberTimePickerState(
                initialHour = LocalDateTime.now().hour,
                initialMinute = LocalDateTime.now().minute
            )

            AlertDialog(
                onDismissRequest = { onEvent.invoke(AddEventEvents.ShowTimeDialog(false)) },
                title = { Text("Select Time") },
                text = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TimePicker(state = timePickerState)
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val finalDateTime = state.selectedDate
                                .withHour(timePickerState.hour)
                                .withMinute(timePickerState.minute)
                            onEvent.invoke(AddEventEvents.ScheduledDateTimeChanged(finalDateTime))
                            onEvent.invoke(AddEventEvents.ShowTimeDialog(false))
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { onEvent.invoke(AddEventEvents.ShowTimeDialog(false)) }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (state.showCategorySheet) {
            CategorySheet(onEvent, viewModel)
        }
    }
}
