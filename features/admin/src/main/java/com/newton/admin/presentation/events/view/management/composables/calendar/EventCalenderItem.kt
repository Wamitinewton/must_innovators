package com.newton.admin.presentation.events.view.management.composables.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.toLocalDateTime
import com.newton.core.domain.models.admin_models.EventsData
import java.time.format.DateTimeFormatter

@Composable
fun EventCalendarItem(
    event: EventsData,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
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
                    text = event.date.toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm")),
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
