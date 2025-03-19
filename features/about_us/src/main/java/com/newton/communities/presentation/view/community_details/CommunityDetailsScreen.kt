package com.newton.communities.presentation.view.community_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.communities.presentation.view.community_details.composables.CommunityHeader
import com.newton.communities.presentation.view.community_details.composables.MemberDetailsDialog
import com.newton.communities.presentation.view.community_details.composables.MembersTab
import com.newton.communities.presentation.view.community_details.composables.OverviewTab
import com.newton.communities.presentation.view.community_details.composables.SessionsTab
import com.newton.communities.presentation.view_model.AboutUsSharedViewModel
import com.newton.core.domain.models.about_us.Community
import com.newton.core.domain.models.about_us.Member
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetailsScreen(
    onBackPressed: () -> Unit,
    onShareClick: (Community) -> Unit,
    onJoinCommunity: (Community) -> Unit,
    aboutUsSharedViewModel: AboutUsSharedViewModel
) {
    val selectedCommunity = aboutUsSharedViewModel.selectedCommunity

    if (selectedCommunity == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                strokeWidth = 4.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        return
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Overview", "Members", "Sessions")
    var showMemberDetails by remember { mutableStateOf<Member?>(null) }
    var isJoined by remember { mutableStateOf(false) }
    var showPulseAnimation by remember { mutableStateOf(false) }

    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        contentVisible = true
    }

    LaunchedEffect(showPulseAnimation) {
        delay(300)
        showPulseAnimation = false
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val isFabVisible by remember { derivedStateOf { scrollBehavior.state.collapsedFraction < 0.5f } }

    DefaultScaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        showOrbitals = true,
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = selectedCommunity.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onShareClick(selectedCommunity) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                    scrolledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
                )
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = contentVisible,
                    enter = fadeIn(animationSpec = tween(500)) +
                            expandVertically(animationSpec = tween(500)),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        elevation = CardDefaults.elevatedCardElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        CommunityHeader(community = selectedCommunity)
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    tonalElevation = 2.dp
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                height = 3.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        divider = {}
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                        maxLines = 1
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = when (index) {
                                            0 -> Icons.Filled.Info
                                            1 -> Icons.Filled.Group
                                            else -> Icons.Filled.Event
                                        },
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                AnimatedContent(
                    targetState = selectedTabIndex,
                    transitionSpec = {
                        (slideInVertically { height -> height } + fadeIn(animationSpec = tween(300))) togetherWith
                                (slideOutVertically { height -> -height } + fadeOut(animationSpec = tween(300)))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) { targetTab ->
                    OutlinedCard(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                        )
                    ) {
                        when (targetTab) {
                            0 -> OverviewTab(
                                community = selectedCommunity,
                            )
                            1 -> MembersTab(
                                members = selectedCommunity.members,
                                onMemberClick = { member -> showMemberDetails = member },
                            )
                            2 -> SessionsTab(
                                sessions = selectedCommunity.sessions,
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = isFabVisible,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        isJoined = !isJoined
                        if (!isJoined) return@FloatingActionButton

                        onJoinCommunity(selectedCommunity)
                        showPulseAnimation = true
                    },
                    containerColor = if (isJoined)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.primary,
                    contentColor = if (isJoined)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape,
                    modifier = Modifier.graphicsLayer {
                        if (showPulseAnimation) {
                            scaleX = 1.1f
                            scaleY = 1.1f
                        }
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = if (isJoined) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isJoined) "Leave Community" else "Join Community"
                        )
                        Text(
                            text = if (isJoined) "Joined" else "Join",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }

    if (showMemberDetails != null) {
        MemberDetailsDialog(
            member = showMemberDetails!!,
            onDismiss = { showMemberDetails = null }
        )
    }
}