/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.events.presentation.view.eventList

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.paging.*
import androidx.paging.compose.*
import com.newton.commonUi.ui.*
import com.newton.core.enums.*
import com.newton.events.presentation.viewmodel.*
import com.newton.network.domain.models.adminModels.*
import kotlinx.coroutines.*
import java.time.*

@Composable
fun EventsScreen(
    eventViewModel: EventViewModel,
    onEventClick: (EventsData) -> Unit,
    onRsvpClick: (EventsData) -> Unit
) {
    val pagingItems = eventViewModel.pagedEvents.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val upcomingEvents = remember(pagingItems.itemSnapshotList.items) {
        pagingItems.itemSnapshotList.items
            .filter { it.date.toLocalDateTime().isAfter(LocalDateTime.now()) }
            .sortedBy { it.date.toLocalDateTime() }
    }

    val pastEvents = remember(pagingItems.itemSnapshotList.items) {
        pagingItems.itemSnapshotList.items
            .filter { it.date.toLocalDateTime().isBefore(LocalDateTime.now()) }
            .sortedByDescending { it.date.toLocalDateTime() }
    }

    val pagerState = rememberPagerState(pageCount = { EventCategory.entries.size })
    val selectedTabIndex = pagerState.currentPage

    val isLoading = pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount == 0
    val isError = pagingItems.loadState.refresh is LoadState.Error
    val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading && pagingItems.itemCount > 0

    DefaultScaffold(
        showOrbitals = true,
        topBar = {
            EventsTopBar(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        },
        snackbarHostState = snackBarHostState
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    EventShimmerList(count = 3)
                }

                isError -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    ErrorScreen(
                        titleText = "Failed to Load Events",
                        message = error.error.localizedMessage ?: "Something went wrong",
                        onRetry = { pagingItems.refresh() },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                pagingItems.itemCount == 0 -> {
                    EmptyEventsCard(modifier = Modifier.fillMaxSize())
                }

                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) { page ->
                            when (page) {
                                EventCategory.UPCOMING.ordinal -> {
                                    EventCategoryContentWithPullToRefresh(
                                        events = upcomingEvents,
                                        emptyMessage = "No upcoming events",
                                        onEventClick = onEventClick,
                                        onRsvpClick = onRsvpClick,
                                        isRefreshing = isRefreshing,
                                        onRefresh = { pagingItems.refresh() }
                                    )
                                }

                                EventCategory.PAST.ordinal -> {
                                    EventCategoryContentWithPullToRefresh(
                                        events = pastEvents,
                                        emptyMessage = "No past events",
                                        onEventClick = onEventClick,
                                        onRsvpClick = onRsvpClick,
                                        isRefreshing = isRefreshing,
                                        onRefresh = { pagingItems.refresh() }
                                    )
                                }
                            }
                        }
                    }
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
