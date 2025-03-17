package com.newton.admin.presentation.events.view.management.composables.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.admin_models.EventsData
import java.util.Locale


@Composable
fun DashboardStats(events: List<EventsData>) {
//    val totalAttendees = events.sumOf { it.members.size }
//    val confirmedAttendees = events.sumOf { event ->
//        event.members.count { true }
//    }
//    val averageAttendanceRate = if (totalAttendees > 0) {
//        (confirmedAttendees.toFloat() / totalAttendees) * 100
//    } else {
//        0f
//    }
//    val totalFeedbacks = events.sumOf { it.feedbacks.size }
//    val averageRating = if (totalFeedbacks > 0) {
//        events.flatMap { it.feedbacks }.sumOf { it.rating }.toFloat() / totalFeedbacks
//    } else {
//        0f
//    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatCard(
            title = "Events",
            value = events.size.toString(),
            icon = Icons.Default.Event,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            title = "Attendees",
            value = "3",
            icon = Icons.Default.People,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            title = "Attendance Rate",
            value ="12%",
            icon = Icons.Default.Percent,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            title = "Avg Rating",
            value = "3%",
            icon = Icons.Default.Star,
            modifier = Modifier.weight(1f)
        )
    }
}
