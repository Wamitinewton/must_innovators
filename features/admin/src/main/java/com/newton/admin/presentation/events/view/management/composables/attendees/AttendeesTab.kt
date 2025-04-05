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
package com.newton.admin.presentation.events.view.management.composables.attendees

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import java.time.*
import java.time.format.*

@Composable
fun AttendeesTab(
    events: List<EventsData>,
    onEvent: (EventEvents) -> Unit,
    viewModel: EventsViewModel
) {
    var selectedEvent by remember { mutableStateOf<EventsData?>(null) }
    val allEvents =
        events.sortedByDescending { it.date.toLocalDateTime().isAfter(LocalDateTime.now()) }
    var expanded by remember { mutableStateOf(false) }
    val attendeesState by viewModel.rsvpState.collectAsState()

    LaunchedEffect(Unit) {
        allEvents.firstOrNull()?.let { firstEvent ->
            selectedEvent = firstEvent
            onEvent.invoke(EventEvents.GetEventsAttendees(firstEvent.id))
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier =
            Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(4.dp)
                )
                .fillMaxWidth()
                .height(40.dp)
                .clickable { expanded = true }
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Select Event",
                style = MaterialTheme.typography.bodyLarge
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                modifier = Modifier.align(Alignment.CenterEnd)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier =
                Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            ) {
                allEvents.forEach { event ->
                    DropdownMenuItem(
                        onClick = {
                            selectedEvent = event
                            expanded = false
                            onEvent.invoke(EventEvents.GetEventsAttendees(event.id))
                        },
                        text = {
                            Text(text = event.name)
                        }
                    )
                }
            }
        }
        selectedEvent = selectedEvent ?: allEvents.firstOrNull()

        if (selectedEvent != null) {
            Text(
                text = "Attendees for: ${selectedEvent!!.name}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "Date: ${
                selectedEvent!!.date.toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
                }",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            if (attendeesState.isLoading) {
                AttendeesShimmer()
            } else if (attendeesState.isSuccess && attendeesState.attendees.isNotEmpty()) {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AttendanceStatistics(
                        attended = attendeesState.attendees.size,
                        confirmed = 8,
                        total = attendeesState.attendees.size
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Attendees list
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(attendeesState.attendees) { member ->
                        AttendeeItem(attendee = member)
                    }
                }
            } else if (attendeesState.hasError != null) {
                OopsError(
                    errorMessage = attendeesState.hasError!!
                )
            } else if (attendeesState.attendees.isEmpty()) {
                OopsError(
                    errorMessage = "There are no attendees right now try again letter",
                    onClick = {
                        onEvent.invoke(EventEvents.GetEventsAttendees(selectedEvent!!.id))
                    },
                    showButton = true
                )
            }
        } else {
            OopsError(
                errorMessage = "No available event available now"
            )
        }
    }
}
