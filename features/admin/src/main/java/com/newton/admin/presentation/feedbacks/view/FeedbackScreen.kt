package com.newton.admin.presentation.feedbacks.view

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.feedbacks.events.*
import com.newton.admin.presentation.feedbacks.view.composables.*
import com.newton.admin.presentation.feedbacks.viewmodel.*
import com.newton.commonUi.composables.*

@Composable
fun FeedbackScreen(
    viewModel: AdminFeedbackViewModel,
    navigateToFeedbackDetail: (Int) -> Unit = {},
    onEvent: (FeedbackEvent) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    var isSearchVisible by remember { mutableStateOf(false) }

    DefaultScaffold(
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
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(end = 40.dp),
                            placeholder = { Text("Search feedback...") },
                            singleLine = true
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
        }
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
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
                modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (uiState.isLoading) {
                    FeedbackCardShimmer()
                } else if (uiState.isSuccess) {
                    val filteredFeedbacks = viewModel.getFilteredFeedbacks()
                    if (filteredFeedbacks.isEmpty()) {
                        EmptyState(
                            message =
                            if (uiState.searchQuery.isNotEmpty()) {
                                "No results found for \"${uiState.searchQuery}\""
                            } else {
                                "No feedback items available"
                            }
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
