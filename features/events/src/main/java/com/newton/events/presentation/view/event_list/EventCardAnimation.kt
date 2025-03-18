package com.newton.events.presentation.view.event_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.newton.common_ui.composables.ScrollMetricsState
import com.newton.common_ui.ui.AnimatedErrorScreen
import com.newton.common_ui.ui.AnimatedSearchPullToRefreshLazyColumn
import com.newton.common_ui.ui.CustomSearchBar
import com.newton.common_ui.ui.EmptySearchResults
import com.newton.common_ui.ui.PaginationLoadingIndicator
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.enums.ScreenMode
import com.newton.events.presentation.states.SearchUiState
import com.newton.events.presentation.view.composables.EventCardShimmerList
import kotlinx.coroutines.launch


@Composable
 fun EventListView(
    pagingItems: androidx.paging.compose.LazyPagingItems<EventsData>,
    scrollState: ScrollMetricsState,
    onEventClick: (EventsData) -> Unit,
    onRsvpClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedSearchPullToRefreshLazyColumn(
            items = pagingItems.itemSnapshotList.items,
            content = { event ->
                EventCard(
                    event = event,
                    onClick = { onEventClick(event) },
                    onRsvpClick = {
                        onEventClick(event)
                        onRsvpClick()
                    }
                )
            },
            isRefreshing = pagingItems.loadState.refresh is LoadState.Loading,
            onRefresh = { pagingItems.refresh() },
            modifier = Modifier.fillMaxSize(),
            scrollMetricsState = scrollState,
        )

        AnimatedVisibility(
            visible = pagingItems.loadState.append is LoadState.Loading &&
                    pagingItems.itemCount > 0,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            PaginationLoadingIndicator()
        }
    }
}


@Composable
 fun SearchResultsView(
    searchResults: List<EventsData>,
    onEventClick: (EventsData) -> Unit,
    onRsvpClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(
            items = searchResults,
            key = { _, event -> event.id }
        ) { _, event ->
            EventCard(
                event = event,
                onClick = { onEventClick(event) },
                onRsvpClick = {
                    onEventClick(event)
                    onRsvpClick()
                }
            )
        }
    }
}

@Composable
 fun SearchBarComponent(
    searchQuery: String,
    screenMode: ScreenMode,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClearClick: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    scrollState: ScrollMetricsState
) {
    CustomSearchBar(
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        onSearchClick = onSearchClick,
        onClearClick = onClearClick,
        scrollOffset = scrollState.scrollValue,
        active = screenMode == ScreenMode.SEARCHING,
        onActiveChange = onActiveChange
    )
}

@Composable
 fun HandleErrorEffects(
    refreshState: LoadState,
    uiState: SearchUiState,
    snackbarHostState: SnackbarHostState,
    scope: kotlinx.coroutines.CoroutineScope,
    itemCount: Int
) {
    LaunchedEffect(refreshState) {
        if (refreshState is LoadState.Error && itemCount > 0) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = refreshState.error.message ?: "Error refreshing events",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    LaunchedEffect(uiState is SearchUiState.Error) {
        if (uiState is SearchUiState.Error) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = (uiState as SearchUiState.Error).message,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}


@Composable
 fun LoadingStateView() {
    EventCardShimmerList()
}

@Composable
 fun SearchLoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
 fun ErrorStateView(message: String, onRetry: () -> Unit) {
    AnimatedErrorScreen(
        message = message,
        onRetry = onRetry
    )
}

@Composable
 fun EmptySearchView(searchQuery: String, onClearSearch: () -> Unit) {
    EmptySearchResults(
        message = "No events found matching '$searchQuery'",
        onClearSearch = onClearSearch
    )
}
