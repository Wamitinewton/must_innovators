package com.newton.events.presentation.view.eventList

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.adminModels.*
import com.newton.events.presentation.viewmodel.*

@Composable
fun EventsScreen(
    eventViewModel: EventViewModel,
    onEventClick: (EventsData) -> Unit,
    onRsvpClick: (EventsData) -> Unit
) {
    val pagingItems = eventViewModel.pagedEvents.collectAsLazyPagingItems()

    val isInitialLoading =
        pagingItems.loadState.refresh is LoadState.Loading &&
            pagingItems.itemCount == 0

    DefaultScaffold(
        showOrbitals = true,
        topBar = {
            EventsTopBar()
        }
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
        ) {
            when {
                isInitialLoading -> {
                    EventShimmerList(count = 3)
                }

                pagingItems.loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    ErrorScreen(
                        titleText = "failed to load CLUB EVENTS",
                        message = error.error.localizedMessage ?: "Something went wrong",
                        onRetry = { pagingItems.refresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                pagingItems.itemCount == 0 && pagingItems.loadState.refresh !is LoadState.Loading -> {
                    EmptyEventsCard(modifier = Modifier.fillMaxSize())
                }

                else -> {
                    EventListView(
                        pagingItems = pagingItems,
                        onEventClick = onEventClick,
                        onRsvpClick = onRsvpClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            AnimatedVisibility(
                visible = pagingItems.loadState.append is LoadState.Loading,
                enter = fadeIn(tween(200)),
                exit = fadeOut(tween(200)),
                modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                PaginationLoadingIndicator()
            }
        }
    }
}
