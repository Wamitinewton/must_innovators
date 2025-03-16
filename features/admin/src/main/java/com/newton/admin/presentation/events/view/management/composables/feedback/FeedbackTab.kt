package com.newton.admin.presentation.events.view.management.composables.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.viewmodel.EventsViewModel
import com.newton.core.domain.models.admin_models.EventsData

@Composable
fun FeedbackTab(
    events: List<EventsData>,
    listState: LazyListState,
    isScrolling: Boolean,
    onEvent:(EventEvents)->Unit,
    viewModel:EventsViewModel
) {
    var selectedEvent by remember { mutableStateOf<EventsData?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val feedbackState by viewModel.feedbacksState.collectAsState()
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
        if (feedbackState.feedbacks.isNotEmpty()){

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
            val averageRating = if (allFeedbacks.isNotEmpty()) {
                allFeedbacks.map { it.rating }.average()
            } else {
                0.0
            }

            Row(
                modifier = Modifier
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
                    text = String.format(null,"%.1f", averageRating),
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
                        allFeedbacks.forEach {feedback->
                            FeedbackCard(
                                feedback = feedback,
                                isScrolling = isScrolling
                            )
                        }

                    }
                }
            }
        }else{
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