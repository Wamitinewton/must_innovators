package com.newton.admin.presentation.events.view.management.composables

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.view.management.Event

@Composable
fun FeedbackTab(
    events: List<Event>,
    listState: LazyListState,
    isScrolling: Boolean
) {
    val allFeedbacks = events.flatMap { event ->
        event.feedbacks.map { feedback ->
            val attendee = event.attendees.find { it.id == feedback.attendeeId }
            Triple(event, feedback, attendee)
        }
    }.sortedByDescending { it.second.submittedAt }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Event Feedback",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Average rating
        val averageRating = if (allFeedbacks.isNotEmpty()) {
            allFeedbacks.map { it.second.rating }.average()
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
                items(allFeedbacks) { (event, feedback, attendee) ->
                    FeedbackCard(
                        event = event,
                        feedback = feedback,
                        attendeeName = attendee?.name ?: "Unknown Attendee",
                        isScrolling = isScrolling
                    )
                }
            }
        }
    }
}