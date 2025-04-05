import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.text.selection.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import com.newton.admin.presentation.notification.events.*
import com.newton.admin.presentation.notification.viewmodel.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*
import java.time.*
import java.time.format.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterAdminScreen(
    viewModel: NotificationsViewModel,
    onEvent: (NotificationEvent) -> Unit,
    navController: NavController
) {
    val newsState by viewModel.notificationState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MeruInnovatorsAppBar("Compose a Newsletter")
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors =
                CardDefaults.cardColors(
                    MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Subject",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newsState.subject,
                        onValueChange = { onEvent.invoke(NotificationEvent.SubjectChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter newsletter subject") },
                        singleLine = true,
                        keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Message",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Row {
                            IconButton(
                                onClick = { onEvent.invoke(NotificationEvent.ShowLinkDialog(shown = true)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Link,
                                    contentDescription = "Add Link"
                                )
                            }

                            IconButton(
                                onClick = {
                                    onEvent.invoke(
                                        NotificationEvent.MessageChange(message = newsState.message + "\n• ")
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                                    contentDescription = "Add Bullet Point"
                                )
                            }

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Image placeholder added")
                                    }
                                    onEvent.invoke(
                                        NotificationEvent.MessageChange(
                                            message = newsState.message + "\n[IMAGE PLACEHOLDER]\n"
                                        )
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = "Add Image"
//                                    tint = accentColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Custom message editor with borders
                    SelectionContainer {
                        BasicTextField(
                            value = newsState.message,
                            onValueChange = { onEvent.invoke(NotificationEvent.MessageChange(it)) },
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp),
                            textStyle =
                            MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            keyboardOptions =
                            KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Default
                            ),
                            decorationBox = { innerTextField ->

                                Box {
                                    if (newsState.message.isEmpty()) {
                                        Text(
                                            text = "Compose your newsletter message here. You can add links, bullet points, and image placeholders using the buttons above.",
                                            color = Color.Gray,
                                            fontSize = 16.sp
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${newsState.message.length} characters",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        fontSize = 12.sp
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Will be sent to 1,245 subscribers"
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "Schedule for later"
                        )
                        Text(
                            text = "Send your newsletter at the optimal time",
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Switch(
                        checked = newsState.isScheduled,
                        onCheckedChange = { onEvent.invoke(NotificationEvent.ScheduledChanged(it)) }
                    )
                }
            }

            AnimatedVisibility(visible = newsState.isScheduled) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier =
                        Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Schedule Date and Time",
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = if (newsState.isScheduled && newsState.scheduledDateTime == null) MaterialTheme.colorScheme.error else Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable { onEvent.invoke(NotificationEvent.ShowDateDialog(true)) }
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = "Select Date"
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text =
                                    if (newsState.scheduledDateTime != null) {
                                        val formatter =
                                            DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a")
                                        newsState.scheduledDateTime!!.format(formatter)
                                    } else {
                                        "Select date and time"
                                    },
                                    color = if (newsState.scheduledDateTime != null) Color.DarkGray else Color.Gray
                                )
                            }
                        }
                        if (newsState.isScheduled && newsState.scheduledDateTime == null) {
                            Text(
                                text = "Schedule date and time is required",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                onClick = {
                    if (newsState.subject.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please add a subject")
                        }
                    } else if (newsState.message.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please add message content")
                        }
                    } else {
                        onEvent.invoke(NotificationEvent.SendNewsLetter)
                    }
                },
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Send Newsletter",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
    if (newsState.isLoading) {
        LoadingDialog()
    }
    if (newsState.uploadSuccess) {
        onEvent.invoke(NotificationEvent.ToDefault)
    }
    if (newsState.showDatePicker) {
        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = System.currentTimeMillis()
            )

        DatePickerDialog(
            onDismissRequest = { onEvent.invoke(NotificationEvent.ShowDateDialog(shown = false)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            val date =
                                java.time.Instant.ofEpochMilli(dateMillis)
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
        val timePickerState =
            rememberTimePickerState(
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
                        // Combine date and time
                        val finalDateTime =
                            newsState.selectedDate
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
                        keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Uri
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newsState.linkTitle.isNotBlank() && newsState.linkUrl.isNotBlank()) {
                            onEvent.invoke(
                                NotificationEvent.MessageChange(
                                    message = newsState.message + "[${newsState.linkTitle}](${newsState.linkUrl})"
                                )
                            )
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
