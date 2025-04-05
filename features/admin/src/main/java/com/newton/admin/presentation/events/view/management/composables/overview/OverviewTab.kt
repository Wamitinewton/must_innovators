package com.newton.admin.presentation.events.view.management.composables.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import java.time.*

@Composable
fun OverviewTab(
    events: List<EventsData>,
    listState: LazyListState,
    isScrolling: Boolean,
    onEventSelected: (EventsData) -> Unit
) {
    val upcomingEvents = events.filter { it.date.toLocalDateTime().isAfter(LocalDateTime.now()) }
    val pastEvents = events.filter { it.date.toLocalDateTime().isBefore(LocalDateTime.now()) }

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
                    onClick = {}
                )
            }

            item {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}
