package com.newton.events.presentation.view.search_events

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.common_ui.ui.AnimatedErrorScreen
import com.newton.common_ui.ui.EmptySearchResults
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.common_ui.ui.PaginationLoadingIndicator
import com.newton.common_ui.ui.SearchHeader
import com.newton.core.domain.models.event_models.EventsData
import com.newton.events.presentation.events.SearchEvent
import com.newton.events.presentation.view.composables.EventCardAnimation
import com.newton.events.presentation.view.composables.SuggestionsList
import com.newton.events.presentation.viewmodel.EventViewModel

@Composable
fun EventSearchScreen(
    viewModel: EventViewModel,
    onBackPress: () -> Unit,
    onEventClick: (EventsData) -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val searchResults = viewModel.searchEvents.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchHeader(
            query = viewState.query,
            onQueryChange = { query ->
                viewModel.handleEvent(SearchEvent.Search(query))
            },
            onBackPress = onBackPress,
            onClearClick = {
                viewModel.handleEvent(SearchEvent.ClearSearch)
            }
        )

        AnimatedVisibility(
            visible = viewState.suggestions.isNotEmpty() && viewState.query.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            SuggestionsList(
                suggestions = viewState.suggestions,
                onSuggestionClick = { suggestion ->
                    viewModel.handleEvent(SearchEvent.SuggestionSelected(suggestion))
                }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                viewState.isLoading -> LoadingIndicator()
                viewState.error != null -> {
                    AnimatedErrorScreen(
                        message = viewState.error ?: "Error occurred",
                        onRetry = { searchResults.retry() }
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(
                            count = searchResults.itemCount,
                        ) { index ->
                            searchResults[index]?.let { event ->

                                EventCardAnimation(
                                    event = event,
                                    onClick = {
                                        onEventClick(event)
                                    }
                                )
                            }
                        }
                    }

                    if (searchResults.itemCount == 0 && viewState.query.isNotEmpty()) {
                        EmptySearchResults(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    androidx.compose.animation.AnimatedVisibility(
                        visible = searchResults.loadState.append is LoadState.Loading,
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