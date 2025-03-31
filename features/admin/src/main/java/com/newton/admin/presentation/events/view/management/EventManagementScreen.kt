package com.newton.admin.presentation.events.view.management

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.view.management.composables.attendees.AttendeesTab
import com.newton.admin.presentation.events.view.management.composables.calendar.CalendarTab
import com.newton.admin.presentation.events.view.management.composables.feedback.FeedbackTab
import com.newton.admin.presentation.events.view.management.composables.overview.OverviewTab
import com.newton.admin.presentation.events.view.management.composables.overview.OverviewTabShimmer
import com.newton.admin.presentation.events.viewmodel.EventsViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.OopsError
import com.newton.common_ui.ui.toLocalDate
import com.newton.common_ui.ui.toLocalDateTime
import com.newton.core.domain.models.admin_models.CalendarDay
import com.newton.core.domain.models.admin_models.EventsData
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventManagementScreen(
    onEvent: (EventEvents) -> Unit,
    viewModel: EventsViewModel,
    onEventSelected: (EventsData) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val eventState by viewModel.eventList.collectAsState()
    val events = eventState.events

    LaunchedEffect(key1 = true) {
        val upcomingEvents = events.filter {
            it.isVirtual &&
                    it.date.toLocalDateTime().minusDays(1).isBefore(LocalDateTime.now())
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
    val today = LocalDate.now()
    val calendarDays = remember {
        val days = mutableListOf<CalendarDay>()
        println("Available events with dates:")
        events.forEach {
            Timber.d(
                "Event: ${it.name}, Date string: ${it.date}, Parsed: ${
                    it.date.toLocalDateTime().toLocalDate()
                }"
            )
        }
        for (i in -30..60) {
            val date = today.plusDays(i.toLong())
            val dayEvents = events.filter {
                it.date.toLocalDate() == date
            }
            days.add(CalendarDay(date, dayEvents))
        }
        days
    }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Calendar", "Attendees", "Feedback")
    var selectedEvent by remember { mutableStateOf<EventsData?>(null) }
    val listState = rememberLazyListState()
    val isScrolling by remember {
        derivedStateOf {
            listState.isScrollInProgress
        }
    }



    DefaultScaffold(

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
                            onClick = { onEvent.invoke(EventEvents.LoadEvents) })
                    }
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

