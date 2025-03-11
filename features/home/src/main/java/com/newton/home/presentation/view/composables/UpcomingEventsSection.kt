package com.newton.home.presentation.view.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.core.domain.models.event_models.Event

@Composable
fun UpcomingEventsSection(
    events: List<Event>,
    onRSVPClick: (Int) -> Unit,
    configuration: Configuration
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(events) { event ->
            Card(
                modifier = Modifier.width((configuration.screenWidthDp*0.9).dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = event.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = event.description, minLines = 7, maxLines = 7)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "Organizer")
                        Text(text = event.organizer)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date")
                        Text(text = event.date)
                    }
                    Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Button(
                            onClick = { onRSVPClick(event.id) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (event.isVirtual) Color.Gray else MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Text(text = if (event.isVirtual) "RSVPed" else "RSVP")
                        }
                    }
                }
            }
        }
    }
}
