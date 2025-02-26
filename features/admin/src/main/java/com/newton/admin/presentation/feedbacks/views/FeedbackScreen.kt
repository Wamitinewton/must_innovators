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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Reply
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.AdminAction
import com.newton.admin.domain.models.enums.FeedbackCategory
import com.newton.admin.domain.models.enums.FeedbackPriority
import com.newton.admin.domain.models.enums.FeedbackStatus
import com.newton.admin.presentation.feedbacks.states.FeedbackState
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*


//@Composable
//fun FeedbackScreen(modifier: Modifier = Modifier) {
//    Scaffold {
//        Box(modifier = Modifier.fillMaxSize().padding(it)){
//            FeedbackScreen()
//        }
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
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
    val density = LocalDensity.current

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
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent
                            )
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

                    IconButton(onClick = { viewModel.loadFeedbacks() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
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
                        LoadingState()
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
fun FilterSection(
    selectedFilter: FeedbackStatus?,
    onFilterSelected: (FeedbackStatus?) -> Unit
) {
    val filters = remember {
        listOf(
            null to "All",
            FeedbackStatus.PENDING to "Pending",
            FeedbackStatus.IN_PROGRESS to "In Progress",
            FeedbackStatus.RESOLVED to "Completed"
        )
    }

    ScrollableTabRow(
        selectedTabIndex = filters.indexOfFirst { it.first == selectedFilter }.takeIf { it >= 0 } ?: 0,
        edgePadding = 16.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = {}
    ) {
        filters.forEachIndexed { index, (status, label) ->
            val selected = status == selectedFilter

            Tab(
                selected = selected,
                onClick = { onFilterSelected(status) },
                text = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    }
}

@Composable
fun StatsSummary(feedbacks: List<FeedBack>) {
    val pendingCount = remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.PENDING } }
    val inProgressCount = remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.IN_PROGRESS } }
    val completedCount = remember(feedbacks) { feedbacks.count { it.status == FeedbackStatus.RESOLVED } }
    val criticalCount = remember(feedbacks) { feedbacks.count { it.priority == FeedbackPriority.CRITICAL } }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                value = pendingCount,
                label = "Pending",
                icon = Icons.Outlined.AccessTime,
                color = MaterialTheme.colorScheme.tertiary
            )

            HorizontalDivider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            StatItem(
                value = inProgressCount,
                label = "In Progress",
                icon = Icons.Outlined.Edit,
                color = MaterialTheme.colorScheme.secondary
            )

            HorizontalDivider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            StatItem(
                value = completedCount,
                label = "Completed",
                icon = Icons.Outlined.CheckCircle,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            StatItem(
                value = criticalCount,
                label = "Critical",
                icon = Icons.Outlined.Warning,
                color = Color.Red
            )
        }
    }
}

@Composable
fun StatItem(
    value: Int,
    label: String,
    icon: ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun FeedbackList(
    feedbacks: List<FeedBack>,
    listState: LazyListState,
    onFeedbackClick: (String) -> Unit,
    onActionToggle: (String, AdminAction) -> Unit
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = feedbacks,
            key = { _, feedback -> feedback.id }
        ) { index, feedback ->
            val animatedModifier = remember {
                Modifier.animateItem(
                    fadeInSpec = null, fadeOutSpec = null, placementSpec = spring<IntOffset>(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            }

            FeedbackCard(
                feedback = feedback,
                onCardClick = { onFeedbackClick(feedback.id) },
                onActionToggle = onActionToggle,
                modifier = animatedModifier,
                index = index
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp)) // For FAB space
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackCard(
    feedback: FeedBack,
    onCardClick: () -> Unit,
    onActionToggle: (String, AdminAction) -> Unit,
    modifier: Modifier = Modifier,
    index: Int
) {
    val cardAnimationDelay = 50L * (index % 10)
    var cardVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(cardAnimationDelay)
        cardVisible = true
    }

    val cardScale by animateFloatAsState(
        targetValue = if (cardVisible) 1f else 0.8f,
        animationSpec = tween(
            durationMillis = 300,
            easing = EaseOutBack
        )
    )

    val cardAlpha by animateFloatAsState(
        targetValue = if (cardVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    val priorityColor = when (feedback.priority) {
        FeedbackPriority.LOW -> MaterialTheme.colorScheme.tertiary
        FeedbackPriority.MEDIUM -> MaterialTheme.colorScheme.secondary
        FeedbackPriority.HIGH -> Color(0xFFF57F17) // Amber
        FeedbackPriority.CRITICAL -> Color(0xFFD50000) // Red
    }

    val categoryIcon = when (feedback.category) {
        FeedbackCategory.BUG_REPORT -> Icons.Outlined.BugReport
        FeedbackCategory.FEATURE_REQUEST -> Icons.Outlined.Lightbulb
        FeedbackCategory.GENERAL_INQUIRY -> Icons.AutoMirrored.Outlined.HelpOutline
        FeedbackCategory.ACCOUNT_ISSUE -> Icons.Outlined.AccountCircle
//        FeedbackCategory.PAYMENT_PROBLEM -> Icons.Outlined.CreditCard
        FeedbackCategory.PERFORMANCE_ISSUE -> Icons.Outlined.Speed
    }

    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val formattedDate = remember(feedback.submissionTimestamp) {
        dateFormat.format(Date(feedback.submissionTimestamp))
    }

    Card(
        onClick = onCardClick,
        modifier = modifier
            .fillMaxWidth()
            .scale(cardScale)
            .alpha(cardAlpha)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = priorityColor.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = when (feedback.status) {
                FeedbackStatus.PENDING -> MaterialTheme.colorScheme.surfaceVariant
                FeedbackStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                FeedbackStatus.RESOLVED -> MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Priority indicator
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(priorityColor)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Category chip
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = categoryIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = feedback.category.name.replace("_", " ").lowercase()
                                .split(" ")
                                .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Grammar issue indicator
                if (feedback.hasGrammarIssues) {
                    Icon(
                        imageVector = Icons.Outlined.Spellcheck,
                        contentDescription = "Grammar issues detected",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }

                // Date
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // User info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User avatar
                AsyncImage(
                    model = feedback.userProfilePic,
                    contentDescription = "Profile picture of ${feedback.userName}",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = feedback.userName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = feedback.userEmail,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Status indicator
                val statusColor = when (feedback.status) {
                    FeedbackStatus.PENDING -> MaterialTheme.colorScheme.tertiary
                    FeedbackStatus.IN_PROGRESS -> MaterialTheme.colorScheme.secondary
                    FeedbackStatus.RESOLVED -> MaterialTheme.colorScheme.primary
                }

                val statusText = when (feedback.status) {
                    FeedbackStatus.PENDING -> "Pending"
                    FeedbackStatus.IN_PROGRESS -> "In Progress"
                    FeedbackStatus.RESOLVED -> "Completed"
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = statusColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Feedback content
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (feedback.hasGrammarIssues)
                    MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
                else
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ) {
                Text(
                    text = feedback.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FeedbackActionButton(
                    text = "Write",
                    icon = Icons.Default.Edit,
                    selected = feedback.status == FeedbackStatus.IN_PROGRESS,
                    onClick = { onActionToggle(feedback.id, AdminAction.WRITE) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.width(8.dp))

                FeedbackActionButton(
                    text = "Reply",
                    icon = Icons.Default.Reply,
                    selected = feedback.status == FeedbackStatus.RESOLVED,
                    onClick = { onActionToggle(feedback.id, AdminAction.REPLY) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                FeedbackActionButton(
                    text = "Grammar",
                    icon = Icons.Default.Spellcheck,
                    selected = feedback.hasGrammarIssues,
                    onClick = { onActionToggle(feedback.id, AdminAction.GRAMMAR_CHECK) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (feedback.assignedTo != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Assigned to: ${feedback.assignedTo}",
                        style = MaterialTheme.typography.labelSmall,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun FeedbackActionButton(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    val backgroundColor = if (selected) color.copy(alpha = 0.1f) else Color.Transparent
    val contentColor = if (selected) color else MaterialTheme.colorScheme.onSurfaceVariant

    Button(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        border = if (selected) BorderStroke(1.dp, color) else null,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
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

@Composable
fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Inbox,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Try adjusting your filters or search query",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ErrorState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Error,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Retry action */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Try Again")
            }
        }
    }
}