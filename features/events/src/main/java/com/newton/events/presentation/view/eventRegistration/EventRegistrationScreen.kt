package com.newton.events.presentation.view.eventRegistration

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.*
import com.newton.auth.presentation.login.viewModel.*
import com.newton.commonUi.composables.*
import com.newton.events.presentation.events.*
import com.newton.events.presentation.states.*
import com.newton.events.presentation.viewmodel.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.*

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
