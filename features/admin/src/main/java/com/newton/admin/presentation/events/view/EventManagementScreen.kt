package com.newton.admin.presentation.events.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.newton.admin.domain.models.enums.FeedbackCategory
import com.newton.admin.domain.models.enums.FeedbackPriority
import com.newton.admin.domain.models.enums.FeedbackStatus
import com.newton.core.domain.models.admin_models.CalendarDay
import com.newton.core.domain.models.event_models.EventsData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Attendee(
    val id: String,
    val name: String,
    val email: String,
    val profilePicUrl: String?,
    val isAttending: Boolean,
    val hasCheckedIn: Boolean = false
)

data class FeedBack(
    val id: String,
    val userId: String,
    val userName: String,
    val userProfilePic: String,
    val userEmail: String,
    val content: String,
    val submissionTimestamp: Long,
    val status: FeedbackStatus,
    val priority: FeedbackPriority,
    val category: FeedbackCategory,
    val hasGrammarIssues: Boolean,
    val assignedTo: String? = null
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
                id = 1,
                name = "Annual Tech Conference",
                description = "Our biggest tech event of the year featuring industry speakers and networking opportunities.",
                date = LocalDateTime.now().plusDays(16),
//                endDateTime = LocalDateTime.now().plusDays(16),
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
//                feedbacks = listOf(
//                    Feedback("f1", "a1", 5, "Great event! Learned a lot.", LocalDateTime.now().minusDays(2)),
//                    Feedback("f2", "a2", 4, "Good speakers, but venue was a bit crowded.", LocalDateTime.now().minusDays(1))
//                ),
                isReminderSet = false
            ),
            Event(
                id = "4",
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
//                feedbacks = listOf(
//                    Feedback("f1", "a1", 5, "Great event! Learned a lot.", LocalDateTime.now().minusDays(2)),
//                    Feedback("f2", "a2", 4, "Good speakers, but venue was a bit crowded.", LocalDateTime.now().minusDays(1))
//                ),
                isReminderSet = true
            ),
            Event(
                id = "2",
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
                    Attendee("c1", "Emma Davis", "emma@example.com", null,
                        isAttending = true,
                        hasCheckedIn = false
                    ),
                    Attendee("c2", "Daniel Wilson", "daniel@example.com", profilePicUrl = null,
                        isAttending = false,
                        hasCheckedIn = false
                    ),
                    Attendee("c3", "Olivia Martin", "olivia@example.com", null,
                        isAttending = true,
                        hasCheckedIn = false
                    )
                ),
                isReminderSet = true
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
                it..toLocalDate() == date ||
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
                duration = SnackbarDuration.Long
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
            TabRow(selectedTabIndex = selectedTab, modifier = Modifier.wrapContentHeight()) {
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
fun OverviewTab(
    events: List<Event>,
    listState: LazyListState,
    isScrolling: Boolean,
    onEventSelected: (Event) -> Unit
) {
    val upcomingEvents = events.filter { it.startDateTime.isAfter(LocalDateTime.now()) }
    val pastEvents = events.filter { it.endDateTime.isBefore(LocalDateTime.now()) }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                DashboardStats(events)
            }

            item {
                Text(
                    text = "Upcoming Events (${upcomingEvents.size})",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(upcomingEvents) { event ->
                EventCard(
                    event = event,
                    isScrolling = isScrolling,
                    onClick = { onEventSelected(event) }
                )
            }

            item {
                Text(
                    text = "Past Events (${pastEvents.size})",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(pastEvents) { event ->
                EventCard(
                    event = event,
                    isScrolling = isScrolling,
                    onClick = { onEventSelected(event) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun DashboardStats(events: List<Event>) {
    val totalAttendees = events.sumOf { it.attendees.size }
    val confirmedAttendees = events.sumOf { event ->
        event.attendees.count { it.isAttending }
    }
    val averageAttendanceRate = if (totalAttendees > 0) {
        (confirmedAttendees.toFloat() / totalAttendees) * 100
    } else {
        0f
    }
    val totalFeedbacks = events.sumOf { it.feedbacks.size }
    val averageRating = if (totalFeedbacks > 0) {
        events.flatMap { it.feedbacks }.sumOf { it.rating }.toFloat() / totalFeedbacks
    } else {
        0f
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatCard(
            title = "Events",
            value = events.size.toString(),
            icon = Icons.Default.Event,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            title = "Attendees",
            value = "$confirmedAttendees/$totalAttendees",
            icon = Icons.Default.People,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            title = "Attendance Rate",
            value = String.format(Locale.ENGLISH,"%.1f%%", averageAttendanceRate),
            icon = Icons.Default.Percent,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatCard(
            title = "Avg Rating",
            value = String.format(null,"%.1f⭐", averageRating),
            icon = Icons.Default.Star,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    isScrolling: Boolean,
    onClick: () -> Unit
) {
    val isPast = event.endDateTime.isBefore(LocalDateTime.now())
    val density = LocalDensity.current
    val animatedOffset = remember { Animatable(0f) }
    val infiniteTransition = rememberInfiniteTransition()

    // Subtle animation when scrolling
    LaunchedEffect(isScrolling) {
        if (isScrolling) {
            animatedOffset.animateTo(
                targetValue = 5f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            animatedOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
        }
    }

    // Pulse animation for upcoming events with reminders
    val pulseScale = if (!isPast && event.isReminderSet) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.02f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    } else {
        remember { mutableStateOf(1f) }
    }

    Card(
//        onClick = onClick,
//        backgroundColor = if (isPast) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset {
                IntOffset(y = with(density) { animatedOffset.value.toDp().roundToPx() },  x = 0,)
            }
            .clickable {
                onClick()
            }
            .scale(pulseScale.value)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Event date circle
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = if (isPast) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        else MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = event.startDateTime.format(DateTimeFormatter.ofPattern("dd")),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isPast) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        else MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = event.startDateTime.format(DateTimeFormatter.ofPattern("MMM")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isPast) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        else MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${event.startDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - " +
                            event.endDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.labelMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )

                    Text(
                        text = "${event.attendees.count { it.isAttending }}/${event.attendees.size}",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    if (event.isReminderSet) {
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Reminder Set",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Details",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

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

@Composable
fun EventCalendarItem(
    event: Event,
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
                    text = event.title,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${event.startDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - " +
                            event.endDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = event.location,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${event.attendees.count { it.isAttending }}/${event.attendees.size}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "attendees",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun AttendeesTab(events: List<Event>) {
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    val allEvents = events.sortedByDescending { it.startDateTime }

    Column(modifier = Modifier.fillMaxSize()) {
        // Event selector
        DropdownMenu(
            expanded = false,
            onDismissRequest = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            allEvents.forEach { event ->
                DropdownMenuItem(
                    onClick = { selectedEvent = event },
                    text = {
                        Text(text = event.title)
                    }
                )
            }
        }

        // Current selected event
        selectedEvent = selectedEvent ?: allEvents.firstOrNull()

        if (selectedEvent != null) {
            Text(
                text = "Attendees for: ${selectedEvent!!.title}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Text(
                text = "Date: ${selectedEvent!!.startDateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AttendanceStats(
                    attended = selectedEvent!!.attendees.count { it.hasCheckedIn },
                    confirmed = selectedEvent!!.attendees.count { it.isAttending },
                    total = selectedEvent!!.attendees.size
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            // Attendees list
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(selectedEvent!!.attendees) { attendee ->
                    AttendeeItem(attendee = attendee)
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No events available",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
fun AttendanceStats(
    attended: Int,
    confirmed: Int,
    total: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$attended",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Checked In",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$confirmed",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Confirmed",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$total",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Total Invited",
                style = MaterialTheme.typography.labelSmall
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${if(total > 0) (attended * 100 / total) else 0}%",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Attendance Rate",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun AttendeeItem(attendee: Attendee) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile icon/image
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = attendee.name.first().toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = attendee.name,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = attendee.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (attendee.hasCheckedIn) {
                    Badge(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Text(
                            text = "Checked In",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                } else if (attendee.isAttending) {
                    Badge(
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Text(
                            text = "Confirmed",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    Badge(
                        backgroundColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                        contentColor = MaterialTheme.colorScheme.error
                    ) {
                        Text(
                            text = "Pending",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeedbackTab(
    events: List<Event>,
    listState: LazyListState,
    isScrolling: Boolean
) {
    val allFeedbacks = events.flatMap { event ->
        event.feedbacks.map { feedback ->
            val attendee = event.attendees.find { it.id == feedback.attendeeId }
            Triple(event, feedback, attendee)
        }
    }.sortedByDescending { it.second.submittedAt }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Event Feedback",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Average rating
        val averageRating = if (allFeedbacks.isNotEmpty()) {
            allFeedbacks.map { it.second.rating }.average()
        } else {
            0.0
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Average Rating:",
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(modifier = Modifier.width(8.dp))

            RatingBar(
                rating = averageRating.toFloat(),
                modifier = Modifier.height(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = String.format(null,"%.1f", averageRating),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        if (allFeedbacks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No feedback available yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(allFeedbacks) { (event, feedback, attendee) ->
                    FeedbackCard(
                        event = event,
                        feedback = feedback,
                        attendeeName = attendee?.name ?: "Unknown Attendee",
                        isScrolling = isScrolling
                    )
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(maxRating) { index ->
            val starFill = when {
                index < rating.toInt() -> 1f
                index == rating.toInt() && rating % 1 > 0 -> rating % 1
                else -> 0f
            }

            Box(modifier = Modifier.padding(horizontal = 2.dp)) {
                Icon(
                    imageVector = Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )

                if (starFill > 0) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clipToBounds()
                    )
                }
            }
        }
    }
}

@Composable
fun FeedbackCard(
    event: Event,
    feedback: Feedback,
    attendeeName: String,
    isScrolling: Boolean
) {
    val animatedElevation = remember { Animatable(2f) }
    val animatedRotation = remember { Animatable(0f) }
    val density = LocalDensity.current

    // Cool animations when scrolling
    LaunchedEffect(isScrolling) {
        if (isScrolling) {
            // Elevation animation
            launch {
                animatedElevation.animateTo(
                    targetValue = 8f,
                    animationSpec = tween(300, easing = FastOutSlowInEasing)
                )
                animatedElevation.animateTo(
                    targetValue = 2f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )
            }

            // Slight rotation animation
            launch {
                val direction = if (feedback.id.hashCode() % 2 == 0) 1f else -1f
                animatedRotation.animateTo(
                    targetValue = 1f * direction,
                    animationSpec = tween(200, easing = FastOutSlowInEasing)
                )
                animatedRotation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation = with(density) { animatedElevation.value.dp },
                shape = RoundedCornerShape(16.dp)
            )
            .background(color =  MaterialTheme.colorScheme.surface)
            .rotate(animatedRotation.value),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Event badge
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = feedback.submittedAt.format(DateTimeFormatter.ofPattern("MMM d, yyyy")),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = attendeeName,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )

                    RatingBar(
                        rating = feedback.rating.toFloat(),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                // Animated star rating state
                val animatedRating = remember { mutableFloatStateOf(0f) }

                // Create the animated value in the Composable context
                val animatedRatingValue by animateFloatAsState(
                    targetValue = animatedRating.floatValue,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                )

                // Use LaunchedEffect to control the animation timing
                LaunchedEffect(feedback.id) {
                    animatedRating.floatValue = 0f
                    delay(300)
                    animatedRating.floatValue = feedback.rating.toFloat()
                }

                Surface(
                    shape = CircleShape,
                    color = when (feedback.rating) {
                        5 -> Color(0xFF4CAF50)
                        4 -> Color(0xFF8BC34A)
                        3 -> Color(0xFFFFC107)
                        2 -> Color(0xFFFF9800)
                        else -> Color(0xFFF44336)
                    }
                ) {

                    Text(
                        text = feedback.rating.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Comment with quote style
            Surface(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp),
                        modifier = Modifier
                            .width(4.dp)
                            .height(IntrinsicSize.Max)
                    ) {
                        Spacer(modifier = Modifier.fillMaxHeight())
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = feedback.comment,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
fun EventDetailsDialog(
    event: Event,
    onDismiss: () -> Unit,
    onToggleReminder: (Event) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Event Details",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "${event.startDateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))} at " +
                                "${event.startDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - " +
                                event.endDateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = event.location,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Attendees (${event.attendees.size})",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Reminder",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Switch(
                            checked = event.isReminderSet,
                            onCheckedChange = { onToggleReminder(event) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                // Attendees summary
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val confirmedCount = event.attendees.count { it.isAttending }
                    val confirmedPercentage = (confirmedCount * 100f / event.attendees.size).toInt()

                    LinearProgressIndicator(
                        progress = { confirmedCount.toFloat() / event.attendees.size },
                        modifier = Modifier
                            .weight(1f)
                            .background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "$confirmedPercentage%",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface

                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    ) {
                        Text(text = "Close")
                    }

                    Button(
                        onClick = { /* TODO: Edit event */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = "Edit Event")
                    }
                }
            }
        }
    }
}

@Composable
fun rememberScaffoldState(): ScaffoldState {
    val snackbarHostState = remember { SnackbarHostState() }
    return remember { ScaffoldState(snackBarHostState = snackbarHostState) }
}

data class ScaffoldState(
    val snackBarHostState: SnackbarHostState
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
//                                        color = MaterialTheme.colors.primary
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

enum class SnackbarDuration { Short, Long }

data class SnackbarHostState(
    val currentSnackbarData: Any? = null
) {
    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ): Boolean {
        // In a real app, this would handle showing snackbars
        return true
    }
}

@Composable
fun SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    content: @Composable (Any) -> Unit
) {
    // In a real app, this would display snackbars
    // Here it's a placeholder for structure
}

@Composable
fun Snackbar(
    action: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    // In a real app, this would be a real snackbar
    // Here it's a placeholder for structure
}

// Badge component
@Composable
fun Badge(
    backgroundColor: Color,
    contentColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        content()
    }
}