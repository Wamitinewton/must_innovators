package com.newton.admin.presentation.feedbacks.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Spellcheck
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.AdminAction
import com.newton.admin.domain.models.enums.FeedbackCategory
import com.newton.admin.domain.models.enums.FeedbackPriority
import com.newton.admin.domain.models.enums.FeedbackStatus
import com.newton.admin.presentation.feedbacks.states.FeedbackState
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel
import com.newton.admin.presentation.feedbacks.views.composables.EmptyState
import com.newton.admin.presentation.feedbacks.views.composables.ErrorState
import com.newton.admin.presentation.feedbacks.views.composables.FeedbackList
import com.newton.admin.presentation.feedbacks.views.composables.FilterSection
import com.newton.admin.presentation.feedbacks.views.composables.StatsSummary
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun FeedbackScreen(
    viewModel: AdminFeedbackViewModel,
    navigateToFeedbackDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val feedbacks by viewModel.feedbacks.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val listState = rememberLazyListState()

    var isSearchVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MeruInnovatorsAppBar(
                title = "Feeback Screen",
                actions = {
                    AnimatedVisibility(
                        visible = isSearchVisible,
                        enter = fadeIn() + slideInVertically { -it },
                        exit = fadeOut()
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
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
                            viewModel.setSearchQuery("")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Search"
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
                selectedFilter = selectedFilter,
                onFilterSelected = { viewModel.setFilter(it) }
            )

            // Stats Summary
            StatsSummary(feedbacks = feedbacks)

            // Feedback List
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (uiState) {
                    is FeedbackState.Loading -> {

                    }
                    is FeedbackState.Success -> {
                        val filteredFeedbacks = viewModel.getFilteredFeedbacks()
                        if (filteredFeedbacks.isEmpty()) {
                            EmptyState(
                                message = if (searchQuery.isNotEmpty())
                                    "No results found for \"$searchQuery\""
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
                    }
                    is FeedbackState.Error -> {
                        val message = (uiState as FeedbackState.Error).message
                        ErrorState(message = message)
                    }
                }
            }
        }
    }
}






@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Loading feedback...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

