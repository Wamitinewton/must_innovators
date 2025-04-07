import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newton.admin.presentation.events.view.composables.AdminSuccessScreen
import com.newton.admin.presentation.notification.events.NotificationEvent
import com.newton.admin.presentation.notification.view.composables.AddNewsLetterForm
import com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterAdminScreen(
    viewModel: NotificationsViewModel,
    onEvent: (NotificationEvent) -> Unit,
    navController: NavController
) {
    val newsState by viewModel.notificationState.collectAsState()
    DefaultScaffold(
        topBar = {
            MeruInnovatorsAppBar("Compose a Newsletter")
        },
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
            else->{
                AddNewsLetterForm(newsState,onEvent)
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