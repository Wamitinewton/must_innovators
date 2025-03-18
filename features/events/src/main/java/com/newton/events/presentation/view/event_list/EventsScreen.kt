package com.newton.events.presentation.view.event_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.rememberScrollMetricsState
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.enums.ScreenMode
import com.newton.events.presentation.events.SearchUiEvent
import com.newton.events.presentation.states.SearchUiState
import com.newton.events.presentation.viewmodel.EventViewModel

@Composable
fun EventsScreen(
    eventViewModel: EventViewModel,
    onEventClick: (EventsData) -> Unit,
    onRsvpClick: () -> Unit
) {
    val pagingItems = eventViewModel.pagedEvents.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollMetricsState()
    val focusManager = LocalFocusManager.current

    val screenMode = rememberSaveable { mutableStateOf(ScreenMode.BROWSING) }

    val uiState by eventViewModel.uiState.collectAsState()
    val searchQuery by eventViewModel.searchQuery.collectAsState()

    val searchResults = when (uiState) {
        is SearchUiState.Success -> (uiState as SearchUiState.Success).events
        else -> emptyList()
    }

    val isInitialLoading = pagingItems.loadState.refresh is LoadState.Loading &&
            pagingItems.itemCount == 0 && screenMode.value == ScreenMode.BROWSING

    val refreshState = pagingItems.loadState.refresh

    HandleErrorEffects(refreshState, uiState, snackbarHostState, scope, pagingItems.itemCount)

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
            }
    ) {
        DefaultScaffold(
            snackbarHostState = snackbarHostState,
            showOrbitals = true,
            topBar = {
                SearchBarComponent(
                    searchQuery = searchQuery,
                    screenMode = screenMode.value,
                    onSearchQueryChanged = { query ->
                        eventViewModel.onEvent(SearchUiEvent.Search(query))
                        if (query.isNotEmpty()) {
                            screenMode.value = ScreenMode.SEARCHING
                        }
                    },
                    onSearchClick = {
                        if (searchQuery.isNotEmpty()) {
                            eventViewModel.onEvent(SearchUiEvent.ExecuteSearch)
                            focusManager.clearFocus()
                        }
                    },
                    onClearClick = {
                        eventViewModel.onEvent(SearchUiEvent.ClearSearch)
                        screenMode.value = ScreenMode.BROWSING
                        focusManager.clearFocus()
                    },
                    onActiveChange = { isActive ->
                        if (!isActive && searchQuery.isEmpty()) {
                            screenMode.value = ScreenMode.BROWSING
                        }
                    },
                    scrollState = scrollState
                )
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isInitialLoading -> LoadingStateView()

                    uiState is SearchUiState.Loading && screenMode.value == ScreenMode.SEARCHING ->
                        SearchLoadingView()

                    refreshState is LoadState.Error && pagingItems.itemCount == 0 &&
                            screenMode.value == ScreenMode.BROWSING ->
                        ErrorStateView(
                            message = refreshState.error.message ?: "Error Loading Events",
                            onRetry = { pagingItems.retry() }
                        )

                    uiState is SearchUiState.Error && screenMode.value == ScreenMode.SEARCHING ->
                        ErrorStateView(
                            message = (uiState as SearchUiState.Error).message,
                            onRetry = { eventViewModel.onEvent(SearchUiEvent.RetrySearch) }
                        )

                    uiState is SearchUiState.Empty && screenMode.value == ScreenMode.SEARCHING ->
                        EmptySearchView(
                            searchQuery = searchQuery,
                            onClearSearch = {
                                eventViewModel.onEvent(SearchUiEvent.ClearSearch)
                                screenMode.value = ScreenMode.BROWSING
                            }
                        )

                    screenMode.value == ScreenMode.SEARCHING && searchResults.isNotEmpty() ->
                        SearchResultsView(
                            searchResults = searchResults,
                            onEventClick = onEventClick,
                            onRsvpClick = onRsvpClick
                        )

                    else ->
                        EventListView(
                            pagingItems = pagingItems,
                            scrollState = scrollState,
                            onEventClick = onEventClick,
                            onRsvpClick = onRsvpClick
                        )
                }
            }
        }
    }
}


