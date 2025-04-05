package com.newton.admin.presentation.events.view.management.composables.attendees

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*

@Composable
fun AttendanceStatistics(
    attended: Int,
    confirmed: Int,
    total: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$attended",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Checked In",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$confirmed",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Confirmed",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$total",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Total Invited",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${if (total > 0) (attended * 100 / total) else 0}%",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Attendance Rate",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
