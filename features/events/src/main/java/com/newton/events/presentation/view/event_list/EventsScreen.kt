package com.newton.events.presentation.view.event_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.common_ui.ui.AnimatedErrorScreen
import com.newton.common_ui.ui.PaginationLoadingIndicator
import com.newton.common_ui.ui.PullToRefreshLazyColumn
import com.newton.core.domain.models.event_models.EventsData
import com.newton.events.presentation.view.composables.CustomAppBar
import com.newton.events.presentation.view.composables.EventCardAnimation
import com.newton.events.presentation.view.composables.EventCardShimmerList
import com.newton.events.presentation.viewmodel.EventViewModel
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    eventViewModel: EventViewModel,
    onEventClick: (EventsData) -> Unit,
    onSearchClick: () -> Unit,
    onRsvpClick: () -> Unit
) {
    val pagingItems = eventViewModel.pagedEvents.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isInitialLoading = pagingItems.loadState.refresh is LoadState.Loading &&
            pagingItems.itemCount == 0

    val refreshState = pagingItems.loadState.refresh

    LaunchedEffect(refreshState) {
        if (refreshState is LoadState.Error && pagingItems.itemCount > 0) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = refreshState.error.message ?: "Error refreshing events"
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBar(
                onSearchCardClick = onSearchClick
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValue ->
        Box(
            modifier = Modifier.padding(
                top = paddingValue.calculateTopPadding(),
                bottom = paddingValue.calculateBottomPadding(),
            )
        ) {
            when {
                isInitialLoading -> {
                    EventCardShimmerList()
                }

                refreshState is LoadState.Error && pagingItems.itemCount == 0 -> {
                    AnimatedErrorScreen(
                        message = refreshState.error.message ?: "Error Loading Events",
                        onRetry = { pagingItems.retry() }
                    )
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        PullToRefreshLazyColumn(
                            items = pagingItems.itemSnapshotList.items,
                            content = { event ->
                                EventCardAnimation(
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
                        )

                        AnimatedVisibility(
                            visible = pagingItems.loadState.append is LoadState.Loading && pagingItems.itemCount > 0,
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
            }
        }
    }
}