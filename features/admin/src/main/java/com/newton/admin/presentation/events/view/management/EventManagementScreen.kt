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
package com.newton.admin.presentation.events.view.management

import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.view.management.composables.attendees.*
import com.newton.admin.presentation.events.view.management.composables.calendar.*
import com.newton.admin.presentation.events.view.management.composables.feedback.*
import com.newton.admin.presentation.events.view.management.composables.overview.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.commonUi.ui.*
import com.newton.network.domain.models.adminModels.*
import java.time.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventManagementScreen(
    onEvent: (EventEvents) -> Unit,
    viewModel: EventsViewModel,
    onEventSelected: (EventsData) -> Unit
) {
    val eventState by viewModel.eventList.collectAsState()
    val events = eventState.events
    val today = LocalDate.now()
    val calendarDays = remember(eventState.events) {
        val days: MutableList<CalendarDay> = mutableListOf()
        for (i in -30..60) {
            val date = today.plusDays(i.toLong())
            val dayEvents: MutableList<EventsData> = mutableListOf()
            events.forEach {
                if (it.date.toLocalDate() == date) {
                    dayEvents.add(it)
                }
            }
            days.add(CalendarDay(date, dayEvents))
        }
        days
    }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Calendar", "Attendees", "Feedback")
    val listState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            listState.isScrollInProgress
        }
    }
    DefaultScaffold(

        topBar = {
            TopAppBar(
                title = { Text("Events Management") }
            )
        },
        bottomBar = {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
        }

    ) {
        when (selectedTab) {
            0 -> {
                when {
                    eventState.isLoading -> {
                        OverviewTabShimmer()
                    }

                    eventState.events.isNotEmpty() -> {
                        OverviewTab(
                            events,
                            listState,
                            isScrolling,
                            onEventSelected = onEventSelected
                        )
                    }

                    eventState.hasError != null -> {
                        OopsError(
                            errorMessage = eventState.hasError!!,
                            showButton = true,
                            onClick = { onEvent.invoke(EventEvents.LoadEvents) }
                        )
                    }
                }
            }

            1 -> CalendarTab(calendarDays)
            2 -> AttendeesTab(
                events,
                onEvent = onEvent,
                viewModel = viewModel
            )

            3 -> FeedbackTab(
                events,
                listState,
                isScrolling,
                onEvent = onEvent,
                viewModel = viewModel
            )
        }
    }
}
