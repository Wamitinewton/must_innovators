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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.newton.common_ui.ui.AnimatedErrorScreen
import com.newton.common_ui.ui.EmptySearchResults
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.common_ui.ui.SearchHeader
import com.newton.core.domain.models.admin_models.EventsData
import com.newton.core.utils.Resource
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
    val searchResults by viewModel.searchResults.collectAsState()
    val pagedEvents = viewModel.pagedEvents.collectAsLazyPagingItems()
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
                viewState.query.isEmpty() -> {
                    Text(
                        text = "Enter a search term to find events",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )

                    // Show paged events in background
                    EventsList(
                        events = pagedEvents,
                        onEventClick = onEventClick
                    )
                }

                else -> {
                    when (searchResults) {
                        is Resource.Error -> {
                            AnimatedErrorScreen(
                                message = (searchResults as Resource.Error).message ?: "",
                                onRetry = {
                                    viewModel.handleEvent(SearchEvent.Search(viewState.query))
                                }
                            )
                        }
                        is Resource.Loading -> {
                            LoadingIndicator()
                        }
                        is Resource.Success -> {
                            val events = (searchResults as Resource.Success).data
                            if (events.isNullOrEmpty()) {
                                EmptySearchResults(
                                    message = "No events found for '${viewState.query}'",
                                )
                            } else {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(bottom = 64.dp),
                                    state = rememberLazyListState()
                                ) {
                                    items(
                                        count = events.size,
                                    ) { index ->
                                        EventCardAnimation(
                                            event = events[index],
                                            onClick = { onEventClick(events[index]) },
                                            onRsvpClick = {}
                                        )
                                    }
                                }
                            }
                        }
                        null -> {
                            LoadingIndicator()
                        }
                    }
                }
                }
            }


        }
    }

@Composable
private fun EventsList(
    events: androidx.paging.compose.LazyPagingItems<EventsData>,
    onEventClick: (EventsData) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 64.dp),
        state = rememberLazyListState()
    ) {
        items(
            count = events.itemCount,
        ) { index ->
            events[index]?.let { event ->
                EventCardAnimation(
                    event = event,
                    onClick = { onEventClick(event) },
                    onRsvpClick = {}
                )
            }
        }
    }

    AnimatedVisibility(
        visible = events.loadState.append is androidx.paging.LoadState.Loading,
        modifier = Modifier
            .padding(bottom = 16.dp),
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        LoadingIndicator()
    }
}