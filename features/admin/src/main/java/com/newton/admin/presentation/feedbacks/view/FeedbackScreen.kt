package com.newton.admin.presentation.feedbacks.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.feedbacks.events.FeedbackEvent
import com.newton.admin.presentation.feedbacks.view.composables.EmptyState
import com.newton.admin.presentation.feedbacks.view.composables.ErrorState
import com.newton.admin.presentation.feedbacks.view.composables.FeedbackCardShimmer
import com.newton.admin.presentation.feedbacks.view.composables.FeedbackList
import com.newton.admin.presentation.feedbacks.view.composables.FilterSection
import com.newton.admin.presentation.feedbacks.view.composables.StatsSummary
import com.newton.admin.presentation.feedbacks.view.composables.StatsSummaryShimmer
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel
import com.newton.common_ui.composables.MeruInnovatorsAppBar


@Composable
fun FeedbackScreen(
    viewModel: AdminFeedbackViewModel,
    navigateToFeedbackDetail: (String) -> Unit = {},
    onEvent: (FeedbackEvent) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    var isSearchVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MeruInnovatorsAppBar(
                title = "Feedback Screen",
                actions = {
                    AnimatedVisibility(
                        visible = isSearchVisible,
                        enter = fadeIn() + slideInVertically { -it },
                        exit = fadeOut()
                    ) {
                        TextField(
                            value = uiState.searchQuery,
                            onValueChange = { onEvent.invoke(FeedbackEvent.SearchQueryChange(it)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 40.dp),
                            placeholder = { Text("Search feedback...") },
                            singleLine = true,
                        )
                    }

                    if (!isSearchVisible) {
                        IconButton(onClick = { isSearchVisible = true }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    } else {
                        IconButton(onClick = {
                            isSearchVisible = false
                            onEvent.invoke(FeedbackEvent.SearchQueryChange(""))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Search",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter Chips
            FilterSection(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = { onEvent.invoke(FeedbackEvent.SelectedFilterChange(it)) },
                onEvent = onEvent
            )
            if (uiState.isLoading) {
                StatsSummaryShimmer()
            } else if (uiState.isSuccess) {
                StatsSummary(feedbacks = uiState.feedbacks)
            } else {
                Spacer(modifier = Modifier.height(1.dp))
            }
            // Feedback List
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (uiState.isLoading) {
                    FeedbackCardShimmer()
                } else if (uiState.isSuccess) {
                    val filteredFeedbacks = viewModel.getFilteredFeedbacks()
                    if (filteredFeedbacks.isEmpty()) {
                        EmptyState(
                            message = if (uiState.searchQuery.isNotEmpty())
                                "No results found for \"${uiState.searchQuery}\""
                            else
                                "No feedback items available"
                        )
                    } else {
                        FeedbackList(
                            feedbacks = filteredFeedbacks,
                            listState = listState,
                            onFeedbackClick = navigateToFeedbackDetail,
                            onActionToggle = { feedbackId, action ->
                                viewModel.updateFeedbackAction(feedbackId, action)
                            }
                        )
                    }
                } else {
                    ErrorState(message = uiState.errorMessage ?: "Unknown error occurred")
                }
            }
        }
    }
}