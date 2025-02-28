import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsletterAdminScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var subject by remember { mutableStateOf("") }
    var messageContent by remember { mutableStateOf("") }
    var showLinkDialog by remember { mutableStateOf(false) }
    var linkText by remember { mutableStateOf("") }
    var linkUrl by remember { mutableStateOf("") }
    var isScheduled by remember { mutableStateOf(false) }

    // Date and time picker state
    var scheduledDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Compose Newsletter",
                        fontWeight = FontWeight.Bold
                    )
                },
//                actions = {
//                    IconButton(onClick = {
//                        scope.launch {
//                            snackbarHostState.showSnackbar("Newsletter preview generated")
//                        }
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.Visibility,
//                            contentDescription = "Preview",
//                        )
//                    }
//                    IconButton(onClick = {
//                        if (subject.isBlank()) {
//                            scope.launch {
//                                snackbarHostState.showSnackbar("Please add a subject")
//                            }
//                        } else if (messageContent.isBlank()) {
//                            scope.launch {
//                                snackbarHostState.showSnackbar("Please add message content")
//                            }
//                        } else {
//                            scope.launch {
//                                snackbarHostState.showSnackbar("Newsletter sent successfully!")
//                            }
//                        }
//                    }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.Send,
//                            contentDescription = "Send",
//                            tint = Color.White
//                        )
//                    }
//                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Subject card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Subject",
                        fontWeight = FontWeight.Bold,
//                        color = primaryColor,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter newsletter subject") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
//                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = Color.LightGray,
//                            cursorColor = primaryColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    )
                }
            }

            // Message card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
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
//                            color = primaryColor,
                            fontSize = 16.sp
                        )

                        Row {
                            IconButton(
                                onClick = { showLinkDialog = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Link,
                                    contentDescription = "Add Link",
//                                    tint = accentColor
                                )
                            }

                            IconButton(
                                onClick = {
                                    messageContent += "\n• "
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
                                    contentDescription = "Add Bullet Point",
//                                    tint = accentColor
                                )
                            }

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Image placeholder added")
                                    }
                                    messageContent += "\n[IMAGE PLACEHOLDER]\n"
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = "Add Image",
//                                    tint = accentColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Custom message editor with borders
                    SelectionContainer {
                        BasicTextField(
                            value = messageContent,
                            onValueChange = { messageContent = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp),
                            textStyle = MaterialTheme.typography.bodySmall,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Default
                            ),
                            decorationBox = { innerTextField ->

                                Box {
                                    if (messageContent.isEmpty()) {
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
                        text = "${messageContent.length} characters",
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Will be sent to 1,245 subscribers",
                    )
                }
            }

            // Schedule card with toggle
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
//                        tint = primaryColor
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "Schedule for later",
                        )
                        Text(
                            text = "Send your newsletter at the optimal time",
                            fontSize = 12.sp,
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))


                    Switch(
                        checked = isScheduled,
                        onCheckedChange = { isScheduled = it },
//                        colors = SwitchDefaults.colors(
//                            checkedThumbColor = primaryColor,
//                            checkedTrackColor = primaryColor.copy(alpha = 0.5f)
//                        )
                    )
                }
            }
            // Date and time selector - visible only when scheduled is true
            AnimatedVisibility(visible = isScheduled) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = "Schedule Date and Time",
                            fontWeight = FontWeight.Medium,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Date and time selector
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = if (isScheduled && scheduledDateTime == null) MaterialTheme.colorScheme.error else Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable { showDatePicker = true }
                                .padding(16.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = "Select Date",
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = if (scheduledDateTime != null) {
                                        val formatter =
                                            DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a")
                                        scheduledDateTime!!.format(formatter)
                                    } else {
                                        "Select date and time"
                                    },
                                    color = if (scheduledDateTime != null) Color.DarkGray else Color.Gray
                                )
                            }
                        }
                        if (isScheduled && scheduledDateTime == null) {
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

            // Send button
            Button(
                onClick = {
                    if (subject.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please add a subject")
                        }
                    } else if (messageContent.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please add message content")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Newsletter sent successfully!")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = primaryColor
//                ),
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

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = System.currentTimeMillis()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { dateMillis ->
                            val date = java.time.Instant.ofEpochMilli(dateMillis)
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDateTime()
                            selectedDate = date
                            showDatePicker = false
                            showTimePicker = true
                        }
                    }
                ) {
                    Text("Next")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Time picker dialog
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = LocalDateTime.now().hour,
            initialMinute = LocalDateTime.now().minute
        )

        AlertDialog(
            onDismissRequest = { showTimePicker = false },
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
                        val finalDateTime = selectedDate
                            .withHour(timePickerState.hour)
                            .withMinute(timePickerState.minute)

                        scheduledDateTime = finalDateTime
                        showTimePicker = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTimePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

// Add link dialog
    if (showLinkDialog) {
        AlertDialog(
            onDismissRequest = {
                showLinkDialog = false
                linkText = ""
                linkUrl = ""
            },
            title = {
                Text("Add Link")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = linkText,
                        onValueChange = { linkText = it },
                        label = { Text("Link Text") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = linkUrl,
                        onValueChange = { linkUrl = it },
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
                        if (linkText.isNotBlank() && linkUrl.isNotBlank()) {
                            // In a real app, you'd insert a clickable link
                            // Here we're just adding formatted text
                            messageContent += "[${linkText}](${linkUrl})"
                            showLinkDialog = false
                            linkText = ""
                            linkUrl = ""
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showLinkDialog = false
                        linkText = ""
                        linkUrl = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}