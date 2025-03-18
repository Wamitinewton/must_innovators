package com.newton.admin.presentation.events.view.management

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.view.management.composables.attendees.AttendeesTab
import com.newton.admin.presentation.events.view.management.composables.calendar.CalendarTab
import com.newton.admin.presentation.events.view.management.composables.feedback.FeedbackTab
import com.newton.admin.presentation.events.view.management.composables.overview.OverviewTab
import com.newton.admin.presentation.events.view.management.composables.overview.OverviewTabShimmer
import com.newton.admin.presentation.events.viewmodel.EventsViewModel
import com.newton.common_ui.ui.fromStringToLocalTime
import com.newton.core.domain.models.admin_models.CalendarDay
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.navigation.NavigationRoutes
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventManagementScreen(
    navController: NavController,
    onEvent: (EventEvents) -> Unit,
    viewModel: EventsViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val eventState by viewModel.eventList.collectAsState()
    val events = eventState.events
    // Calendar preparation
    val today = LocalDate.now()
    val calendarDays = remember {
        val days = mutableListOf<CalendarDay>()
        for (i in -30..60) {
            val date = today.plusDays(i.toLong())
            val dayEvents = events.filter {
                it.date.fromStringToLocalTime().toLocalDate() == date
            }
            days.add(CalendarDay(date, dayEvents))
        }
        days
    }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Calendar", "Attendees", "Feedback")
    var selectedEvent by remember { mutableStateOf<EventsData?>(null) }
    // Animation for the feedback card
    val listState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            listState.isScrollInProgress
        }
    }

    LaunchedEffect(key1 = events) {
        // Check if any events need reminders
        val upcomingEvents = events.filter {
            it.isVirtual &&
                    it.date.fromStringToLocalTime().minusDays(1).isBefore(LocalDateTime.now())
        }

        if (upcomingEvents.isNotEmpty()) {
            val eventNames = upcomingEvents.joinToString(", ") { it.name }
            scaffoldState.snackBarHostState.showSnackBar(
                message = "Reminder: You have upcoming events: $eventNames",
                actionLabel = "View",
                duration = SnackBarDuration.Long
            )
        }
    }

    Scaffold(
//        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Events Management") },
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
        },

        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {
            when (selectedTab) {
                0 -> {
                    when (eventState.isLoading) {
                        true -> OverviewTabShimmer()
                        false -> OverviewTab(
                            events,
                            listState,
                            isScrolling,
                            onEventSelected = {
                                onEvent.invoke(EventEvents.SelectedEvent(it))
                            })
                    }

                }

                1 -> CalendarTab(calendarDays, onEventSelected = { selectedEvent = it })
                2 -> AttendeesTab(
                    events,
                    onEvent = onEvent,
                    viewModel = viewModel
                )

                3 -> FeedbackTab(
                    events, listState, isScrolling,
                    onEvent = onEvent,
                    viewModel = viewModel
                )
            }
            if (eventState.selectedEvent != null) {
                navController.navigate(NavigationRoutes.ModifyEvent.routes){
                    popUpTo(NavigationRoutes.AdminEvents.routes)
                }
            }
        }
    }
}

@Composable
fun rememberScaffoldState(): ScaffoldState {
    val snackBarHostState = remember { SnackBarHostState() }
    return remember { ScaffoldState(snackBarHostState = snackBarHostState) }
}

data class ScaffoldState(
    val snackBarHostState: SnackBarHostState
)

@Composable
fun Scaffold(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            topBar()

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                content(PaddingValues(bottom = 8.dp))
            }

            bottomBar()
        }
    }
}

enum class SnackBarDuration { Short, Long }

data class SnackBarHostState(
    val currentSnackBarData: Any? = null
) {
    fun showSnackBar(
        message: String,
        actionLabel: String? = null,
        duration: SnackBarDuration = SnackBarDuration.Short
    ): Boolean {
        return true
    }
}

