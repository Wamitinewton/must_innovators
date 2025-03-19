package com.newton.events.presentation.view.event_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.ErrorScreen
import com.newton.common_ui.ui.PaginationLoadingIndicator
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.events.presentation.viewmodel.EventViewModel

@Composable
fun EventsScreen(
    eventViewModel: EventViewModel,
    onEventClick: (EventsData) -> Unit,
    onRsvpClick: () -> Unit
) {
    val pagingItems = eventViewModel.pagedEvents.collectAsLazyPagingItems()


    DefaultScaffold(
        showOrbitals = true,
        topBar = {
            EventsTopBar()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            when {

                pagingItems.loadState.refresh is LoadState.Loading -> {
                    EventShimmerList(count = 3)
                }

                pagingItems.loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    ErrorScreen(
                        message = error.error.localizedMessage ?: "Something went wrong",
                        onRetry = { pagingItems.refresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                pagingItems.itemCount == 0 -> {
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
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                PaginationLoadingIndicator()
            }
        }

    }
}

