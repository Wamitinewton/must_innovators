package com.newton.events.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.common_ui.ui.PaginationLoadingIndicator
import com.newton.events.presentation.view.composables.CustomAppBar
import com.newton.events.presentation.view.composables.EventCardAnimation
import com.newton.events.presentation.viewmodel.EventViewModel
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(
    modifier: Modifier = Modifier,
    eventViewModel: EventViewModel
) {
    val pagingItems = eventViewModel.events.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomAppBar(
                selectedCategory = "",
                categories = emptyList(),
                searchInput = ""
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
                            contentPadding = PaddingValues(bottom = 64.dp)
                        ) {
                            items(
                                count = pagingItems.itemCount,
                                key = { index -> pagingItems[index]?.id ?: index }
                            ) {index ->
                                pagingItems[index]?.let { events ->
                                    EventCardAnimation(
                                        event = events,
                                        onClick = {}
                                    )
                                }
                            }

                            if (pagingItems.loadState.append is LoadState.Loading) {
                                item { Spacer(modifier = Modifier.height(60.dp)) }
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

                    LaunchedEffect(pagingItems.loadState) {
                        when {
                            pagingItems.loadState.append is LoadState.Error -> {
                                val error = (pagingItems.loadState.append as LoadState.Error).error.message
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = error ?: "Error Loading more events",
                                        actionLabel = "Retry",
                                        duration = SnackbarDuration.Short
                                    ).let { result ->
                                        when(result) {
                                            SnackbarResult.ActionPerformed -> pagingItems.retry()
                                            else -> {}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}
