package com.newton.admin.presentation.events.view.management.composables.attendees

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.viewmodel.EventsViewModel
import com.newton.common_ui.composables.OopsError
import com.newton.common_ui.ui.fromStringToLocalTime
import com.newton.core.domain.models.admin_models.EventsData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AttendeesTab(
    events: List<EventsData>,
    onEvent: (EventEvents) -> Unit,
    viewModel: EventsViewModel
) {
    var selectedEvent by remember { mutableStateOf<EventsData?>(null) }
    val allEvents =
        events.sortedByDescending { it.date.fromStringToLocalTime().isAfter(LocalDateTime.now()) }
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
            modifier = Modifier
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
                modifier = Modifier
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
                    selectedEvent!!.date.fromStringToLocalTime()
                        .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
                }",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            if (attendeesState.isLoading) {
                AttendeesShimmer()
            } else if (attendeesState.isSuccess && attendeesState.attendees.isNotEmpty()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AttendanceStatistics(
                        attended = attendeesState.attendees.size,
                        confirmed = 8,
                        total = attendeesState.attendees.size,
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
            }else if(attendeesState.hasError != null){
                OopsError(
                    errorMessage = attendeesState.hasError!!,
                )
            }else if (attendeesState.attendees.isEmpty()){
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
                errorMessage = "No available event available now",
                onClick = {
                    onEvent.invoke(EventEvents.GetEventsAttendees(selectedEvent!!.id))
                },
                showButton = true
            )
        }
    }
}
