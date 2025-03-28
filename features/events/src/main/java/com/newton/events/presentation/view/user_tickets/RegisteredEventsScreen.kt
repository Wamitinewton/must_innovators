package com.newton.events.presentation.view.user_tickets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.events.presentation.viewmodel.UserTicketsViewModel

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
                colors = TopAppBarDefaults.topAppBarColors(
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