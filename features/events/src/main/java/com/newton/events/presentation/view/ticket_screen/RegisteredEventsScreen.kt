package com.newton.events.presentation.view.ticket_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.events.presentation.view.composables.EmptyTicketsView
import com.newton.events.presentation.view.composables.EventTicketCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisteredEventsScreen(
    onBackPressed: () -> Unit,
    onTicketSelected: (String) -> Unit = {}
) {

    val tickets = remember {
        listOf(
            EventTicket(
                id = "1",
                ticketNumber = "TECH24-5781",
                eventName = "Google Developer Conference",
                eventDescription = "Join us for the latest in Android and web development",
                eventDate = "2024-12-05T14:00:00Z",
                eventLocation = "Technology Hub, Main Auditorium",
                registrationDate = "2024-12-05T14:00:00Z",
                ticketType = TicketType.STANDARD
            ),
            EventTicket(
                id = "2",
                ticketNumber = "HACK23-1234",
                eventName = "Spring Hackathon 2025",
                eventDescription = "48-hour coding challenge with amazing prizes",
                eventDate = "2025-12-05T14:00:00Z",
                eventLocation = "Innovation Center, Floor 3",
                registrationDate = "2024-12-05T14:00:00Z",
                ticketType = TicketType.EARLY_BIRD
            ),
            EventTicket(
                id = "3",
                ticketNumber = "WORK25-7890",
                eventName = "UI/UX Design Workshop",
                eventDescription = "Learn the fundamentals of modern UI/UX design",
                eventDate = "2025-12-05T14:00:00Z",
                eventLocation = "Design Studio, Room 101",
                registrationDate = "2024-12-05T14:00:00Z"
            ),
            EventTicket(
                id = "4",
                ticketNumber = "CONF24-4567",
                eventName = "AI & Machine Learning Summit",
                eventDescription = "Exploring the frontiers of artificial intelligence",
                eventDate = "2025-12-05T14:00:00Z",
                eventLocation = "Science Center, Grand Hall",
                registrationDate = "2024-12-05T14:00:00Z",
                ticketType = TicketType.STANDARD,
                isUsed = true
            )
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Event Tickets") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->

        if (tickets.isEmpty()){
            EmptyTicketsView(modifier = Modifier.padding(paddingValues))
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                        )
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                item {
                    Text(
                        text = "Your upcoming events",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                val upcomingTickets = tickets.filter { !it.isUsed }
                val pastEvents = tickets.filter { it.isUsed }

                items(upcomingTickets) { ticket ->
                    EventTicketCard(
                        ticket = ticket,
                        onClick = {}
                    )
                }

                if (pastEvents.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Past Events",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(pastEvents) { ticket ->
                        EventTicketCard(
                            ticket = ticket,
                            onClick = {}
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}