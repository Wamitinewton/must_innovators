package com.newton.admin.presentation.events.view.management.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.view.management.Event
import java.time.format.DateTimeFormatter

@Composable
fun AttendeesTab(events: List<Event>) {
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    val allEvents = events.sortedByDescending { it.startDateTime }

    Column(modifier = Modifier.fillMaxSize()) {
        // Event selector
        DropdownMenu(
            expanded = false,
            onDismissRequest = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            allEvents.forEach { event ->
                DropdownMenuItem(
                    onClick = { selectedEvent = event },
                    text = {
                        Text(text = event.title)
                    }
                )
            }
        }

        // Current selected event
        selectedEvent = selectedEvent ?: allEvents.firstOrNull()

        if (selectedEvent != null) {
            Text(
                text = "Attendees for: ${selectedEvent!!.title}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "Date: ${selectedEvent!!.startDateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AttendanceStatistics(
                    attended = selectedEvent!!.attendees.count { it.hasCheckedIn },
                    confirmed = selectedEvent!!.attendees.count { it.isAttending },
                    total = selectedEvent!!.attendees.size
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Attendees list
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(selectedEvent!!.attendees) { attendee ->
                    AttendeeItem(attendee = attendee)
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No events available",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
