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
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import timber.log.*
import java.time.*

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
        val upcomingEvents =
            events.filter {
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
    val calendarDays =
        remember {
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
                val dayEvents =
                    events.filter {
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

            1 -> CalendarTab(calendarDays, onEventSelected = { selectedEvent = it })
            2 ->
                AttendeesTab(
                    events,
                    onEvent = onEvent,
                    viewModel = viewModel
                )

            3 ->
                FeedbackTab(
                    events,
                    listState,
                    isScrolling,
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
