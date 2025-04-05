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
