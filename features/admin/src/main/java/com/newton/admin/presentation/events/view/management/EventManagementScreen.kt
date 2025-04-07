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

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.view.management.composables.attendees.AttendeesTab
import com.newton.admin.presentation.events.view.management.composables.calendar.CalendarTab
import com.newton.admin.presentation.events.view.management.composables.feedback.FeedbackTab
import com.newton.admin.presentation.events.view.management.composables.overview.OverviewTab
import com.newton.admin.presentation.events.view.management.composables.overview.OverviewTabShimmer
import com.newton.admin.presentation.events.viewmodel.EventsViewModel
import com.newton.commonUi.composables.DefaultScaffold
import com.newton.commonUi.composables.OopsError
import com.newton.commonUi.ui.toLocalDate
import com.newton.network.domain.models.adminModels.CalendarDay
import com.newton.network.domain.models.adminModels.EventsData
import java.time.LocalDate

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
