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
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.network.domain.models.adminModels.*

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
        modifier =
            Modifier
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
            value = "12%",
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
