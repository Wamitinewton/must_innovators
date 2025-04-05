package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.admin.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDialog(
    session: Session?,
    onDismiss: () -> Unit,
    onSave: (Session) -> Unit
) {
    val isEditing = session != null

    var day by remember { mutableStateOf(session?.day ?: "MONDAY") }
    var startTime by remember { mutableStateOf(session?.start_time ?: "09:00") }
    var endTime by remember { mutableStateOf(session?.end_time ?: "10:00") }
    var meetingType by remember { mutableStateOf(session?.meeting_type ?: "VIRTUAL") }
    var location by remember { mutableStateOf(session?.location ?: "") }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    // For Day dropdown
    var expanded by remember { mutableStateOf(false) }
    val days = listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")

    // For Meeting Type dropdown
    var meetingTypeExpanded by remember { mutableStateOf(false) }
    val meetingTypes = listOf("VIRTUAL", "PHYSICAL", "HYBRID")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEditing) "Edit Session" else "Add New Session") },
        text = {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                // Day dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = day,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        label = { Text("Day") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Day") },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        days.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    day = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Start Time
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = "Start Time"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showStartTimePicker = true }) {
                            Icon(Icons.Default.Schedule, contentDescription = "Pick time")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // End Time
                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End Time") },
                    leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = "End Time") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showEndTimePicker = true }) {
                            Icon(Icons.Default.Schedule, contentDescription = "Pick time")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Meeting Type dropdown
                ExposedDropdownMenuBox(
                    expanded = meetingTypeExpanded,
                    onExpandedChange = { meetingTypeExpanded = !meetingTypeExpanded }
                ) {
                    OutlinedTextField(
                        value = meetingType,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = meetingTypeExpanded) },
                        label = { Text("Meeting Type") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.VideoCall,
                                contentDescription = "Meeting Type"
                            )
                        },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = meetingTypeExpanded,
                        onDismissRequest = { meetingTypeExpanded = false }
                    ) {
                        meetingTypes.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    meetingType = option
                                    meetingTypeExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Location
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Location"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        Session(
                            day = day,
                            start_time = startTime,
                            end_time = endTime,
                            meeting_type = meetingType,
                            location = location
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    // Time pickers
    if (showStartTimePicker) {
        TimePickerDialog(
            onDismiss = { showStartTimePicker = false },
            onTimeSelected = {
                startTime = it
                showStartTimePicker = false
            },
            initialTime = startTime
        )
    }

    if (showEndTimePicker) {
        TimePickerDialog(
            onDismiss = { showEndTimePicker = false },
            onTimeSelected = {
                endTime = it
                showEndTimePicker = false
            },
            initialTime = endTime
        )
    }
}
