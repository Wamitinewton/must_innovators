package com.newton.admin.presentation.community.view.composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.domain.models.Session


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDialog(
    session: Session?,
    onDismiss: () -> Unit,
    onSave: (Session) -> Unit
) {
    val isEditing = session != null

    var day by remember { mutableStateOf(session?.date ?: "MONDAY") }
    var startTime by remember { mutableStateOf(session?.startTime ?: "09:00") }
    var endTime by remember { mutableStateOf(session?.endTime ?: "10:00") }
    var meetingType by remember { mutableStateOf(session?.sessionType ?: "VIRTUAL") }
    var location by remember { mutableStateOf(session?.location ?: "") }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    // For Day dropdown
    var expanded by remember { mutableStateOf(false) }
    val days = listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")

    // For Meeting Type dropdown
    var meetingTypeExpanded by remember { mutableStateOf(false) }
    val meetingTypes = listOf("VIRTUAL", "IN_PERSON", "HYBRID")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEditing) "Edit Session" else "Add New Session") },
        text = {
            Column(
                modifier = Modifier
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
                        modifier = Modifier
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
                    leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = "Start Time") },
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
                        leadingIcon = { Icon(Icons.Default.VideoCall, contentDescription = "Meeting Type") },
                        modifier = Modifier
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
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Location") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        Session(
                            date = day,
                            startTime = startTime,
                            endTime = endTime,
                            sessionType = meetingType,
                            location = location,
                            title = ""
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
