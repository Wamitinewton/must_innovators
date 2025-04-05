package com.newton.events.presentation.view.userTickets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.core.data.response.admin.*
import com.newton.events.presentation.states.*

@Composable
fun RegisteredEventsSection(
    uiState: UserTicketsUiState,
    onTicketSelected: (Int) -> Unit = {}
) {
    when (uiState) {
        is UserTicketsUiState.Initial -> InitialView()
        is UserTicketsUiState.Loading -> LoadingIndicator(text = "Loading your tickets...")
        is UserTicketsUiState.Success ->
            UserTicketsView(
                tickets = uiState.tickets,
                onTicketSelected = onTicketSelected
            )

        is UserTicketsUiState.Error ->
            ErrorScreen(
                message = uiState.message,
                onRetry = {},
                titleText = "Could not load your EVENT TICKETS"
            )
    }
}

@Composable
fun InitialView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ready to explore your events",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun UserTicketsView(
    tickets: List<RegistrationResponse>,
    onTicketSelected: (Int) -> Unit
) {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors =
                    listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                    )
                )
            )
    ) {
        if (tickets.isEmpty()) {
            EmptyTicketsView()
        } else {
            LazyColumn(
                modifier =
                Modifier
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
                        onClick = { onTicketSelected(ticket.event) }
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
                            onClick = { onTicketSelected(ticket.event) }
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
