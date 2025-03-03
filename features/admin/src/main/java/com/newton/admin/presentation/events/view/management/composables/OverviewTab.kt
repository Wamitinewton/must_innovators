package com.newton.admin.presentation.events.view.management.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.view.management.Event
import java.time.LocalDateTime

@Composable
fun OverviewTab(
    events: List<Event>,
    listState: LazyListState,
    isScrolling: Boolean,
    onEventSelected: (Event) -> Unit
) {
    val upcomingEvents = events.filter { it.startDateTime.isAfter(LocalDateTime.now()) }
    val pastEvents = events.filter { it.endDateTime.isBefore(LocalDateTime.now()) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                DashboardStats(events)
            }

            item {
                Text(
                    text = "Upcoming Events (${upcomingEvents.size})",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(upcomingEvents) { event ->
                EventCard(
                    event = event,
                    isScrolling = isScrolling,
                    onClick = { onEventSelected(event) }
                )
            }

            item {
                Text(
                    text = "Past Events (${pastEvents.size})",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(pastEvents) { event ->
                EventCard(
                    event = event,
                    isScrolling = isScrolling,
                    onClick = { onEventSelected(event) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}