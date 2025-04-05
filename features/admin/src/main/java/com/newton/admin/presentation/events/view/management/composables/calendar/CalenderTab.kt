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
package com.newton.admin.presentation.events.view.management.composables.calendar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.adminModels.*
import java.time.*
import java.time.format.*

@Composable
fun CalendarTab(
    calendarDays: List<CalendarDay>,
    onEventSelected: (EventsData) -> Unit
) {
    val today = LocalDate.now()
    val visibleMonths =
        remember {
            calendarDays.map { it.date.month }.distinct()
        }

    var selectedMonth by remember { mutableStateOf(today.month) }
    var selectedDate by remember { mutableStateOf(today) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(visibleMonths) { month ->
                val isSelected = month == selectedMonth

                Button(
                    onClick = { selectedMonth = month },
                    colors =
                    ButtonDefaults.buttonColors().copy(
                        containerColor =
                        if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = month.toString(),
                        color =
                        if (isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        }

        // Calendar grid
        LazyRow(
            modifier =
            Modifier
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
                    modifier =
                    Modifier
                        .padding(horizontal = 4.dp)
                        .clickable { selectedDate = calendarDay.date }
                ) {
                    Text(
                        text = calendarDay.date.format(DateTimeFormatter.ofPattern("E")),
                        style = MaterialTheme.typography.labelSmall
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier =
                        Modifier
                            .size(40.dp)
                            .background(
                                color =
                                when {
                                    isSelected -> MaterialTheme.colorScheme.primary
                                    isToday -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                    else -> Color.Transparent
                                },
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            text = calendarDay.date.dayOfMonth.toString(),
                            color =
                            when {
                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                else -> MaterialTheme.colorScheme.onSurface
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }

                    if (hasEvents) {
                        Box(
                            modifier =
                            Modifier
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
        val selectedDayEvents = calendarDays.find { it.date == selectedDate }?.events ?: emptyList()

        HorizontalDivider()

        Text(
            text = "Events on ${selectedDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))}",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        if (selectedDayEvents.isEmpty()) {
            Box(
                modifier =
                Modifier
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
