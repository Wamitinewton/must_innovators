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
package com.newton.admin.presentation.events.view.management.composables.feedback

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
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.network.domain.models.adminModels.*

@Composable
fun FeedbackTab(
    events: List<EventsData>,
    listState: LazyListState,
    isScrolling: Boolean,
    onEvent: (EventEvents) -> Unit,
    viewModel: EventsViewModel
) {
    var selectedEvent by remember { mutableStateOf<EventsData?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val feedbackState by viewModel.feedbacksState.collectAsState()
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
                events.forEach { event ->
                    DropdownMenuItem(
                        onClick = {
                            selectedEvent = event
                            expanded = false
                            onEvent.invoke(EventEvents.GetEventFeedbacks(event.id, true))
                        },
                        text = {
                            Text(text = event.name)
                        }
                    )
                }
            }
        }
        if (feedbackState.feedbacks.isNotEmpty()) {
//            val allFeedbacks = events.flatMap { event ->
//                event.feedbacks.map { feedback ->
//                    val attendee = event.attendees.find { it.id == feedback.attendeeId }
//                    Triple(event, feedback, attendee)
//                }
//            }.sortedByDescending { it.second.submittedAt }
            val allFeedbacks = feedbackState.feedbacks
            Text(
                text = "Event Feedback for ${selectedEvent!!.name}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Average rating
            val averageRating =
                if (allFeedbacks.isNotEmpty()) {
                    allFeedbacks.map { it.rating }.average()
                } else {
                    0.0
                }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Average Rating:",
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(modifier = Modifier.width(8.dp))

                RatingBar(
                    rating = averageRating.toFloat(),
                    modifier = Modifier.height(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = String.format(null, "%.1f", averageRating),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            if (allFeedbacks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No feedback available yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(allFeedbacks) {
                        allFeedbacks.forEach { feedback ->
                            FeedbackCard(
                                feedback = feedback,
                                isScrolling = isScrolling
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Select an Event to View the available event feedback",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
