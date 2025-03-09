package com.newton.admin.presentation.events.view.management

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newton.admin.presentation.events.view.management.composables.AttendeesTab
import com.newton.admin.presentation.events.view.management.composables.CalendarTab
import com.newton.admin.presentation.events.view.management.composables.EventDetailsDialog
import com.newton.admin.presentation.events.view.management.composables.FeedbackTab
import com.newton.admin.presentation.events.view.management.composables.OverviewTab
import java.time.LocalDate
import java.time.LocalDateTime

// Data classes
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val location: String,
    val attendees: List<Attendee>,
    val feedbacks: List<Feedback> = emptyList(),
    val isReminderSet: Boolean = false
)

data class Attendee(
    val id: String,
    val name: String,
    val email: String,
    val profilePicUrl: String?,
    val isAttending: Boolean,
    val hasCheckedIn: Boolean = false
)

data class Feedback(
    val id: String,
    val attendeeId: String,
    val rating: Int, // 1-5
    val comment: String,
    val submittedAt: LocalDateTime
)

data class CalendarDay(
    val date: LocalDate,
    val events: List<Event>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventManagementScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    // Sample data
    val events = remember {
        mutableStateListOf(
            Event(
                id = "1",
                title = "Annual Tech Conference",
                description = "Our biggest tech event of the year featuring industry speakers and networking opportunities.",
                startDateTime = LocalDateTime.now().plusDays(16),
                endDateTime = LocalDateTime.now().plusDays(16),
                location = "Convention Center",
                attendees = listOf(
                    Attendee("a1", "John Doe", "john@example.com", null,
                        isAttending = true,
                        hasCheckedIn = true
                    ),  Attendee("a1", "John Doe", "john@example.com", null,
                        isAttending = true,
                        hasCheckedIn = true
                    ),  Attendee("a1", "John Doe", "john@example.com", null,
                        isAttending = true,
                        hasCheckedIn = true
                    ),  Attendee("a1", "John Doe", "john@example.com", null,
                        isAttending = true,
                        hasCheckedIn = true
                    ),
                    Attendee("a2", "Jane Smith", "jane@example.com", null,
                        isAttending = true,
                        hasCheckedIn = false
                    ),
                    Attendee("a3", "Mike Johnson", "mike@example.com", null,
                        isAttending = false,
                        hasCheckedIn = false
                    )
                ),
                feedbacks = listOf(
                    Feedback("f1", "a1", 5, "Great event! Learned a lot.", LocalDateTime.now().minusDays(2)),
                    Feedback("f2", "a2", 4, "Good speakers, but venue was a bit crowded.", LocalDateTime.now().minusDays(1))
                ),
                isReminderSet = false
            ),
            Event(
                id =" 4",
                title = "Robotics Meeting",
                description = "Our biggest tech event of the year featuring industry speakers and networking opportunities.",
                startDateTime = LocalDateTime.now().plusDays(16),
                endDateTime = LocalDateTime.now().plusDays(16),
                location = "Innovation Center",
                attendees = listOf(
                    Attendee("a1", "John Doe", "john@example.com", null, true, true),
                    Attendee("a2", "Jane Smith", "jane@example.com", null, true, false),
                    Attendee("a3", "Mike Johnson", "mike@example.com", null, false, false)
                ),
                feedbacks = listOf(
                    Feedback("f1", "a1", 5, "Great event! Learned a lot.", LocalDateTime.now().minusDays(2)),
                    Feedback("f2", "a2", 4, "Good speakers, but venue was a bit crowded.", LocalDateTime.now().minusDays(1))
                ),
                isReminderSet = true
            ),
            Event(
                id = "5",
                title = "Product Launch",
                description = "Launch event for our new mobile application.",
                startDateTime = LocalDateTime.now().minusDays(7),
                endDateTime = LocalDateTime.now().minusDays(7),
                location = "Head Office - Main Hall",
                attendees = listOf(
                    Attendee("b1", "Sarah Wilson", "sarah@example.com", null, true, true),
                    Attendee("b2", "Robert Brown", "robert@example.com", null, true, true),
                    Attendee("b3", "Lisa Taylor", "lisa@example.com", null, true, false),
                    Attendee("b4", "James Anderson", "james@example.com", null, false, false)
                ),
                feedbacks = listOf(
                    Feedback("f3", "b1", 5, "Amazing product launch! Very innovative features.", LocalDateTime.now().minusDays(6)),
                    Feedback("f4", "b2", 1, "The speaker was not eard very well.ur biggest tech event of the year featuring industry speakers and networking opportunities. Test of the large text in the feedback card", LocalDateTime.now().minusDays(6))
                )
            ),
            Event(
                id = "3",
                title = "Team Building Workshop",
                description = "A day of team activities focused on improving collaboration.",
                startDateTime = LocalDateTime.now().plusDays(3),
                endDateTime = LocalDateTime.now().plusDays(3),
                location = "Adventure Park",
                attendees = listOf(
                    Attendee(
                        "c1", "Emma Davis", "emma@example.com", null,
                        isAttending = true,
                        hasCheckedIn = false
                    ),
                    Attendee(
                        "c2", "Daniel Wilson", "daniel@example.com", profilePicUrl = null,
                        isAttending = false,
                        hasCheckedIn = false
                    ),
                    Attendee(
                        "c3", "Olivia Martin", "olivia@example.com", null,
                        isAttending = true,
                        hasCheckedIn = false
                    )
                ),
                isReminderSet = true,
                feedbacks = listOf(
                    Feedback("f3", "b1", 5, "Amazing product launch! Very innovative features.", LocalDateTime.now().minusDays(6)),
                    Feedback("f4", "b2", 1, "The speaker was not eard very well.ur biggest tech event of the year featuring industry speakers and networking opportunities. Test of the large text in the feedback card", LocalDateTime.now().minusDays(6))
                )
            )
        )
    }

    // Calendar preparation
    val today = LocalDate.now()
    val calendarDays = remember {
        val days = mutableListOf<CalendarDay>()
        for (i in -30..60) {
            val date = today.plusDays(i.toLong())
            val dayEvents = events.filter {
                it.startDateTime.toLocalDate() == date ||
                        it.endDateTime.toLocalDate() == date
            }
            days.add(CalendarDay(date, dayEvents))
        }
        days
    }

    // Tabs state
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Calendar", "Attendees", "Feedback")

    // Selected event for details
    var selectedEvent by remember { mutableStateOf<Event?>(null) }

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
            it.isReminderSet &&
                    it.startDateTime.isAfter(LocalDateTime.now()) &&
                    it.startDateTime.minusDays(1).isBefore(LocalDateTime.now())
        }

        if (upcomingEvents.isNotEmpty()) {
            val eventNames = upcomingEvents.joinToString(", ") { it.title }
            scaffoldState.snackBarHostState.showSnackbar(
                message = "Reminder: You have upcoming events: $eventNames",
                actionLabel = "View",
                duration = SnackBararDuration.Long
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
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
                0 -> OverviewTab(events, listState, isScrolling, onEventSelected = { selectedEvent = it })
                1 -> CalendarTab(calendarDays, onEventSelected = { selectedEvent = it })
                2 -> AttendeesTab(events)
                3 -> FeedbackTab(events, listState, isScrolling)
            }

            // Event details dialog
            if (selectedEvent != null) {
                EventDetailsDialog(
                    event = selectedEvent!!,
                    onDismiss = { selectedEvent = null },
                    onToggleReminder = { event ->
                        val index = events.indexOfFirst { it.id == event.id }
                        if (index >= 0) {
                            events[index] = event.copy(isReminderSet = !event.isReminderSet)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun rememberScaffoldState(): ScaffoldState {
    val snackbarHostState = remember { SnackBarHostState() }
    return remember { ScaffoldState(snackBarHostState = snackbarHostState) }
}

data class ScaffoldState(
    val snackBarHostState: SnackBarHostState
)

@Composable
fun Scaffold(
    scaffoldState: ScaffoldState,
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
                content(PaddingValues(bottom = 56.dp))

//                // Snackbar
//                SnackbarHost(
//                    hostState = scaffoldState.snackBarHostState,
//                    modifier = Modifier
//                        .align(Alignment.BottomCenter)
//                        .padding(16.dp)
//                ) { snackbarData ->
//                    Snackbar(
//                        action = {
//                            snackbarData.actionLabel?.let { actionLabel ->
//                                TextButton(
//                                    onClick = { snackbarData.performAction() }
//                                ) {
//                                    Text(
//                                        text = actionLabel,
//                                        color = MaterialTheme.colorScheme.primary
//                                    )
//                                }
//                            }
//                        }
//                    ) {
//                        Text(text = snackbarData.message)
//                    }
//                }
            }

            bottomBar()
        }
    }
}

enum class SnackBararDuration { Short, Long }

data class SnackBarHostState(
    val currentSnackbarData: Any? = null
) {
    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackBararDuration = SnackBararDuration.Short
    ): Boolean {
        return true
    }
}

