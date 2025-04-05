package com.newton.events.presentation.view.userTickets

import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.newton.commonUi.composables.*
import com.newton.events.presentation.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisteredEventsScreen(
    onBackPressed: () -> Unit,
    onTicketSelected: (Int) -> Unit = {},
    userTicketsViewModel: UserTicketsViewModel
) {
    val uiState by userTicketsViewModel.userTicketsUiState.collectAsState()

    DefaultScaffold(
        showOrbitals = true,
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
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) {
        RegisteredEventsSection(
            uiState = uiState,
            onTicketSelected = onTicketSelected
        )
    }
}
