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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.common_ui.ui.AnimatedErrorScreen
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.common_ui.ui.PaginationLoadingIndicator
import com.newton.core.domain.models.event_models.EventsData
import com.newton.events.presentation.view.composables.CustomAppBar
import com.newton.events.presentation.view.composables.EventCardAnimation
import com.newton.events.presentation.viewmodel.EventViewModel

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    eventViewModel: EventViewModel,
    onEventClick: (EventsData) -> Unit,
    onSearchClick: () -> Unit,
    onRsvpClick: () -> Unit
) {
    val pagingItems = eventViewModel.searchEvents.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
            when(val refreshLoadState = pagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingIndicator()
                }

                is LoadState.Error -> {
                    AnimatedErrorScreen(
                        message = refreshLoadState.error.message ?: "Error Loading Events",
                        onRetry = { pagingItems.retry() }
                    )
                }
                else -> {

                    Box(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 64.dp),
                            state = rememberLazyListState()
                        ) {
                            items(
                                count = pagingItems.itemCount,
                            ) {index ->
                                pagingItems[index]?.let { events ->
                                    EventCardAnimation(
                                        event = events,
                                        onClick = {
                                            onEventClick(events)
                                        },
                                        onRsvpClick = {
                                            onEventClick(events)
                                            onRsvpClick()
                                        }
                                    )
                                }
                            }

                        }

                        AnimatedVisibility(
                            visible = pagingItems.loadState.append is LoadState.Loading,
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
