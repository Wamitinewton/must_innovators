package com.newton.admin.presentation.events.view.management.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.view.management.CalendarDay
import com.newton.admin.presentation.events.view.management.Event
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarTab(
    calendarDays: List<CalendarDay>,
    onEventSelected: (Event) -> Unit
) {
    val today = LocalDate.now()
    val visibleMonths = remember {
        calendarDays.map { it.date.month }.distinct()
    }

    var selectedMonth by remember { mutableStateOf(today.month) }
    var selectedDate by remember { mutableStateOf(today) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Month selector
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(visibleMonths) { month ->
                val isSelected = month == selectedMonth

                Button(
                    onClick = { selectedMonth = month },
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = month.toString(),
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Calendar grid
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            val daysInSelectedMonth = calendarDays.filter { it.date.month == selectedMonth }

            items(daysInSelectedMonth) { calendarDay ->
                val isSelected = calendarDay.date == selectedDate
                val isToday = calendarDay.date == today
                val hasEvents = calendarDay.events.isNotEmpty()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable { selectedDate = calendarDay.date }
                ) {
                    Text(
                        text = calendarDay.date.format(DateTimeFormatter.ofPattern("E")),
                        style = MaterialTheme.typography.labelSmall
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = when {
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    isToday -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                    else -> Color.Transparent
                                },
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = calendarDay.date.dayOfMonth.toString(),
                            color = when {
                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                else -> MaterialTheme.colorScheme.onSurface
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }

                    if (hasEvents) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.secondary,
                                    shape = CircleShape
                                )
                        )
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Events for selected day
        val selectedDayEvents = calendarDays.find { it.date == selectedDate }?.events ?: emptyList()

        HorizontalDivider()

        Text(
            text = "Events on ${selectedDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))}",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        if (selectedDayEvents.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No events scheduled for this day",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(selectedDayEvents) { event ->
                    EventCalendarItem(
                        event = event,
                        onClick = { onEventSelected(event) }
                    )
                }
            }
        }
    }
}