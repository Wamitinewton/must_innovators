package com.newton.admin.presentation.events.view.management.composables.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import java.time.format.*

@Composable
fun EventCalendarItem(
    event: EventsData,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = event.date.toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = event.location,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(horizontalAlignment = Alignment.End) {
//                Text(
//                    text = "${event.attendees.count { it.isAttending }}/${event.attendees.size}",
//                    style = MaterialTheme.typography.bodyMedium
//                )

//                Text(
//                    text = "attendees",
//                    style = MaterialTheme.typography.labelSmall
//                )
            }
        }
    }
}
