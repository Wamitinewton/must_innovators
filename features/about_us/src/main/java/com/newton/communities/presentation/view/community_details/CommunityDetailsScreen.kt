package com.newton.communities.presentation.view.community_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.newton.communities.presentation.view.community_details.composables.CommunityMembersTab
import com.newton.communities.presentation.view.community_details.composables.CommunityOverviewTab
import com.newton.communities.presentation.view.community_details.composables.CommunitySessionsTab
import com.newton.communities.presentation.view.community_details.composables.CommunityTechStackTab
import com.newton.communities.presentation.view_model.AboutUsSharedViewModel
import com.newton.core.domain.models.about_us.Community
import com.newton.core.utils.formatDateTime
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetailsScreen(
    onBackPressed: () -> Unit,
    onShareClick: (Community) -> Unit,
    onJoinCommunity: (Community) -> Unit,
    aboutUsSharedViewModel: AboutUsSharedViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val tabs = listOf("Overview", "Members", "Sessions", "Tech Stack")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val community = aboutUsSharedViewModel.selectedCommunity

    // Animation for header
    val headerScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "HeaderScale"
    )

    // Animated gradient for membership badge
    val infiniteTransition = rememberInfiniteTransition(label = "GradientTransition")
    val gradientPosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "GradientPosition"
    )

    // Animated gradient colors for recruiting badge
    val recruitingGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.primary
        ),
        start = Offset(0f, 0f),
        end = Offset(gradientPosition * 3000f, 0f)
    )

    DisposableEffect(Unit) {
        onDispose {
            aboutUsSharedViewModel.clearSelectedCommunity()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (community != null) {
                        Text(
                            text = community.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (community != null) {
                            onShareClick(community)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (community != null) {
                        onJoinCommunity(community)
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = "Join Community"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Join Now")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .graphicsLayer {
                        scaleX = headerScale
                        scaleY = headerScale
                    },
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.tertiary
                                )
                            )
                        )
                ) {
                    community?.name?.forEach { _ ->
                        val randomX = remember { Random.nextFloat() * 0.8f + 0.1f }
                        val randomY = remember { Random.nextFloat() * 0.8f + 0.1f }
                        val randomSize = remember { Random.nextFloat() * 20f + 10f }
                        val randomAlpha = remember { Random.nextFloat() * 0.3f + 0.05f }

                        Box(
                            modifier = Modifier
                                .size(randomSize.dp)
                                .align(Alignment.TopStart)
                                .graphicsLayer {
                                    translationX = randomX * 1000
                                    translationY = randomY * 200
                                }
                                .alpha(randomAlpha)
                                .clip(CircleShape)
                                .background(Color.White)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (community != null) {
                            Text(
                                text = community.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        if (community != null) {
                            Text(
                                text = "Founded: ${community.foundingDate}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Badge(
                                containerColor = Color.White.copy(alpha = 0.9f),
                                contentColor = MaterialTheme.colorScheme.primary
                            ) {
                                if (community != null) {
                                    Text(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        text = "${community.totalMembers} members",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))

                            if (community != null) {
                                if (community.isRecruiting) {
                                    BadgedBox(
                                        badge = {
                                            Badge(
                                                containerColor = Color.Transparent,
                                                contentColor = Color.White
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(recruitingGradient)
                                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                                ) {
                                                    Text(
                                                        text = "Recruiting",
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                        }
                                    ) {}
                                }
                            }
                        }
                    }
                }
            }

            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    )
                }
            }
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                when(selectedTabIndex) {
                    0 -> community?.let { CommunityOverviewTab(it) }
                    1 -> if (community != null) {
                        CommunityMembersTab(community.members)
                    }
                    2 -> if (community != null) {
                        CommunitySessionsTab(community.sessions)
                    }
                    3 -> if (community != null) {
                        CommunityTechStackTab(community.techStack)
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}