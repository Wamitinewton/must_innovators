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
package com.newton.admin.presentation.events.view.management

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.admin.presentation.testyr.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import java.time.*
import java.time.format.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyEvent(
    viewModel: AdminEventsSharedViewModel,
    onEvent: (EventUpdateEvent) -> Unit,
    updateEventsViewModel: UpdateEventsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val state by updateEventsViewModel.updateState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearSelectedEvent()
        }
    }

    var isEditing by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            isEditing = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel")
                        }
                    } else {
                        IconButton(onClick = {
                            isEditing = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is UpdateEvent.Error -> {
                OopsError(errorMessage = (uiState as UpdateEvent.Error).message)
            }

            UpdateEvent.Initial -> {
                CircularProgressIndicator()
            }

            is UpdateEvent.Success -> {
                val event = (uiState as UpdateEvent.Success).event
                if (isEditing) {
                    Column(
                        modifier =
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Image preview
                        AsyncImage(
                            model = event.imageUrl,
                            contentDescription = "Event Image",
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        // Form fields
                        OutlinedTextField(
                            value = event.name,
                            onValueChange = { onEvent.invoke(EventUpdateEvent.NameChanged(it)) },
                            label = { Text("Event Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = event.category,
                            onValueChange = { onEvent.invoke(EventUpdateEvent.CategoryChanged(it)) },
                            label = { Text("Category") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = event.description,
                            onValueChange = { onEvent.invoke(EventUpdateEvent.DescriptionChanged(it)) },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )

//                        OutlinedTextField(
//                            value = imageUrl,
//                            onValueChange = { imageUrl = it },
//                            label = { Text("Image URL") },
//                            modifier = Modifier.fillMaxWidth()
//                        )

                        OutlinedTextField(
                            value = event.date.toFormatedDate(),
                            onValueChange = { /* Date picker would be implemented here */ },
                            label = { Text("Date and Time") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = {
                                IconButton(onClick = { /* Show date picker */ }) {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = "Select Date"
                                    )
                                }
                            },
                            readOnly = true
                        )

                        OutlinedTextField(
                            value = event.location,
                            onValueChange = { onEvent.invoke(EventUpdateEvent.LocationChanged(it)) },
                            label = { Text("Location") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = event.organizer,
                            onValueChange = { EventUpdateEvent.OrganizerChanged(it) },
                            label = { Text("Organizer") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = event.contactEmail,
                            onValueChange = { EventUpdateEvent.ContactEmailChanged(it) },
                            label = { Text("Contact Email") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Virtual Event")
                            Spacer(modifier = Modifier.weight(1f))
                            Switch(
                                checked = event.isVirtual,
                                onCheckedChange = { EventUpdateEvent.VirtualChanged(it) }
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        CustomButton(
                            onClick = {
                                onEvent.invoke(EventUpdateEvent.Update(event.id))
                            }
                        ) {
                            Icon(Icons.Default.Save, contentDescription = "save")

                            Text("Update Event", style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                } else {
                    Column(
                        modifier =
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Event Image
                        AsyncImage(
                            model = event.imageUrl,
                            contentDescription = "Event Image",
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Column {
                            Text(
                                text = event.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.wrapContentWidth()
                            ) {
                                Text(
                                    text = event.category,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        HorizontalDivider()

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = "Date",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(event.date.toFormatedDate())
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (event.isVirtual) Icons.Default.Videocam else Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(event.location)
                        }

                        HorizontalDivider()

                        Text(
                            text = "Description",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(event.description)

                        Text(
                            text = "Organizer Information",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Organizer",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(event.organizer)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(event.contactEmail)
                        }

                        if (event.isVirtual) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Row(
                                    modifier =
                                    Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 8.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Videocam,
                                        contentDescription = "Virtual Event",
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Virtual Event",
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if (state.isLoading) {
            LoadingDialog()
        } else if (state.errorMessage != null) {
            OopsError(
                errorMessage = state.errorMessage!!
            )
        } else if (state.uploadSuccess) {
            SuccessScreen()
        }
    }
}

private fun formatDateTime(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
    return "${zonedDateTime.format(dateFormatter)} at ${zonedDateTime.format(timeFormatter)}"
}

private fun formatDateTimeForDisplay(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    return zonedDateTime.format(formatter)
}
