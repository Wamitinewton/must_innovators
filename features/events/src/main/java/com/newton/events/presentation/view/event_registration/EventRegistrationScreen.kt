package com.newton.events.presentation.view.event_registration

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.newton.auth.presentation.login.view_model.GetUserDataViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.events.presentation.events.EventRsvpUiEvent
import com.newton.events.presentation.states.EventDetailsState
import com.newton.events.presentation.viewmodel.EventRsvpViewmodel
import com.newton.events.presentation.viewmodel.EventsSharedViewModel
import com.newton.events.presentation.viewmodel.NavigationEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventRegistrationScreen(
    onClose: () -> Unit,
    onNavigateToSuccess: () -> Unit,
    eventsSharedViewModel: EventsSharedViewModel,
    userDataViewModel: GetUserDataViewModel = hiltViewModel(),
    eventRsvpViewmodel: EventRsvpViewmodel
) {
    val userDataState by userDataViewModel.getUserDataState.collectAsState()
    val eventUiState by eventsSharedViewModel.uiState.collectAsState()
    val event = (eventUiState as? EventDetailsState.Success)?.event ?: return
    val uiState by eventRsvpViewmodel.eventRegistrationState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val educationLevels = listOf("1", "2", "3", "4")
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(userDataState.userData) {
        userDataState.userData?.let { user ->
            eventRsvpViewmodel.onEvent(EventRsvpUiEvent.UpdateFirstName(user.first_name))
            eventRsvpViewmodel.onEvent(EventRsvpUiEvent.UpdateLastName(user.last_name))
            eventRsvpViewmodel.onEvent(EventRsvpUiEvent.UpdateEmail(user.email))
            user.course?.let { eventRsvpViewmodel.onEvent(EventRsvpUiEvent.UpdateCourse(it)) }
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            Timber.e(error)
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Indefinite
                )
                eventRsvpViewmodel.onEvent(EventRsvpUiEvent.ClearError)
            }
        }
    }

    LaunchedEffect(key1 = eventRsvpViewmodel.navigationEvents) {
        eventRsvpViewmodel.navigationEvents.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when (it) {
                    is NavigationEvent.NavigateToTicket -> {
                        onNavigateToSuccess()
                    }
                }
            }
    }

    DefaultScaffold(
        showOrbitals = true,
        snackbarHostState = snackbarHostState,
        isLoading = uiState.isLoading,
        topBar = {
            TopAppBar(
                title = { Text("Event Registration") },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            )
        }
    ) {
        EventRegistrationContent(
            event = event,
            eventRegistrationState = uiState,
            educationLevels = educationLevels,
            onEvent = eventRsvpViewmodel::onEvent
        )
    }
}