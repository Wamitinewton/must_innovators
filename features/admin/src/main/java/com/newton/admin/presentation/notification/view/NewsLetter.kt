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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.events.view.composables.*
import com.newton.admin.presentation.notification.events.*
import com.newton.admin.presentation.notification.view.composables.*
import com.newton.admin.presentation.notification.viewmodel.*
import com.newton.commonUi.ui.*
import java.time.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterAdminScreen(
    viewModel: NotificationsViewModel,
    onEvent: (NotificationEvent) -> Unit
) {
    val newsState by viewModel.notificationState.collectAsState()
    DefaultScaffold(
        topBar = {
            MeruInnovatorsAppBar("Compose a Newsletter")
        }
    ) {
        when {
            newsState.uploadSuccess -> {
                AdminSuccessScreen(
                    onFinish = {
                        onEvent.invoke(NotificationEvent.ToDefault)
                    },
                    text = "News letter sent successfully"
                )
            }

            else -> {
                AddNewsLetterForm(newsState, onEvent)
            }
        }
    }

    if (newsState.showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis()
        )

        DatePickerDialog(
            onDismissRequest = { onEvent.invoke(NotificationEvent.ShowDateDialog(shown = false)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            val date = java.time.Instant.ofEpochMilli(dateMillis)
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDateTime()
                            onEvent.invoke(NotificationEvent.SelectedDateChange(date))
                            onEvent.invoke(NotificationEvent.ShowDateDialog(false))
                            onEvent.invoke(NotificationEvent.ShowTimeDialog(true))
                        }
                    }
                ) {
                    Text("Next")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onEvent.invoke(NotificationEvent.ShowDateDialog(false)) }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (newsState.showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = LocalDateTime.now().hour,
            initialMinute = LocalDateTime.now().minute
        )

        AlertDialog(
            onDismissRequest = { onEvent.invoke(NotificationEvent.ShowTimeDialog(false)) },
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
                        val finalDateTime = newsState.selectedDate
                            .withHour(timePickerState.hour)
                            .withMinute(timePickerState.minute)
                        onEvent.invoke(NotificationEvent.ScheduledDateTimeChanged(finalDateTime))
                        onEvent.invoke(NotificationEvent.ShowTimeDialog(false))
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onEvent.invoke(NotificationEvent.ShowTimeDialog(false)) }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (newsState.showLinkDialog) {
        AlertDialog(
            onDismissRequest = {
                onEvent.invoke(NotificationEvent.ShowLinkDialog(shown = false))
                onEvent.invoke(NotificationEvent.LinkChange(link = ""))
                onEvent.invoke(NotificationEvent.LinkTitleChange(title = ""))
            },
            title = {
                Text("Add Link")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = newsState.linkTitle,
                        onValueChange = {
                            onEvent.invoke(NotificationEvent.LinkTitleChange(it))
                        },
                        label = { Text("Link Text") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newsState.linkUrl,
                        onValueChange = {
                            onEvent.invoke(NotificationEvent.LinkChange(it))
                        },
                        label = { Text("URL") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newsState.linkTitle.isNotBlank() && newsState.linkUrl.isNotBlank()) {
                            onEvent.invoke(NotificationEvent.MessageChange(message = newsState.message + "[${newsState.linkTitle}](${newsState.linkUrl})"))
                            onEvent.invoke(NotificationEvent.ShowLinkDialog(shown = false))
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onEvent.invoke(NotificationEvent.ShowLinkDialog(shown = false))
                        onEvent.invoke(NotificationEvent.LinkChange(link = ""))
                        onEvent.invoke(NotificationEvent.LinkTitleChange(title = ""))
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
