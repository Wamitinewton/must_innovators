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
package com.newton.partners.presentation.view.partnerDetails

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import coil3.request.*
import com.newton.commonUi.ui.*
import com.newton.network.domain.models.homeModels.*
import com.newton.partners.presentation.view.partnerList.*
import com.newton.partners.presentation.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerDetailsScreen(
    partnersSharedViewModel: PartnersSharedViewModel,
    onBackPressed: () -> Unit,
    onSharePartner: (PartnersData) -> Unit,
    onNavigateToWebsite: (String) -> Unit,
    onNavigateToLinkedIn: (String) -> Unit,
    onNavigateToTwitter: (String) -> Unit,
    onContactEmail: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val density = LocalDensity.current
    var showTopBar by remember { mutableStateOf(false) }

    val partnerData = partnersSharedViewModel.selectedPartner

    LaunchedEffect(scrollState.value) {
        showTopBar = scrollState.value > 250
    }

    DefaultScaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                ) {
                    AsyncImage(
                        model =
                            ImageRequest.Builder(context)
                                .data(partnerData?.logo)
                                .crossfade(true)
                                .build(),
                        contentDescription = "${partnerData?.name} background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(
                                    brush =
                                        Brush.verticalGradient(
                                            colors =
                                                listOf(
                                                    Color.Transparent,
                                                    MaterialTheme.colorScheme.surface
                                                )
                                        )
                                )
                    )

                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 16.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors =
                                    CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                elevation =
                                    CardDefaults.cardElevation(
                                        defaultElevation = 8.dp
                                    ),
                                modifier = Modifier.size(100.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AsyncImage(
                                        model =
                                            ImageRequest.Builder(context)
                                                .data(partnerData?.logo)
                                                .crossfade(true)
                                                .build(),
                                        contentDescription = "${partnerData?.name} logo",
                                        contentScale = ContentScale.Crop,
                                        modifier =
                                            Modifier
                                                .fillMaxSize()
                                                .padding(8.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                if (partnerData != null) {
                                    Text(
                                        text = partnerData.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (partnerData != null) {
                                        StatusChip(status = partnerData.status)
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    if (partnerData != null) {
                                        Text(
                                            text = partnerData.type,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }

                    IconButton(
                        onClick = onBackPressed,
                        modifier =
                            Modifier
                                .padding(16.dp)
                                .size(40.dp)
                                .align(Alignment.TopStart)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    IconButton(
                        onClick = {
                            if (partnerData != null) {
                                onSharePartner(partnerData)
                            }
                        },
                        modifier =
                            Modifier
                                .padding(16.dp)
                                .size(40.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    if (partnerData != null) {
                        QuickInfoRow(partnerData)
                    }

                    if (partnerData != null) {
                        ContentSection(
                            title = "About",
                            icon = Icons.Outlined.Info,
                            content = partnerData.description
                        )
                    }

                    if (partnerData != null) {
                        ContactInfoCard(
                            partnerData = partnerData,
                            onContactEmail = { email -> onContactEmail(email) },
                            onNavigateToWebsite = { url -> onNavigateToWebsite(url) },
                            onNavigateToLinkedIn = { linkedIn -> onNavigateToLinkedIn(linkedIn) },
                            onNavigateToTwitter = { twitter -> onNavigateToTwitter(twitter) }
                        )
                    }

                    if (partnerData != null) {
                        PartnershipDetailsCard(partnerData)
                    }

                    if (partnerData != null) {
                        ContentSection(
                            title = "Achievements",
                            icon = Icons.Outlined.EmojiEvents,
                            content = partnerData.achievements
                        )
                    }

                    if (partnerData != null) {
                        ContentSection(
                            title = "Benefits",
                            icon = Icons.Outlined.StarOutline,
                            content = partnerData.benefits
                        )
                    }

                    if (partnerData != null) {
                        ContentSection(
                            title = "Resources",
                            icon = Icons.Outlined.Inventory2,
                            content = partnerData.resources
                        )
                    }

                    if (partnerData != null) {
                        ContentSection(
                            title = "Events Supported",
                            icon = Icons.Outlined.Event,
                            content = partnerData.events_supported
                        )
                    }

                    if (partnerData != null) {
                        ContentSection(
                            title = "Target Audience",
                            icon = Icons.Outlined.Groups,
                            content = partnerData.target_audience
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            AnimatedVisibility(
                visible = showTopBar,
                enter =
                    fadeIn(animationSpec = tween(300)) +
                        slideInVertically(
                            animationSpec = tween(300),
                            initialOffsetY = { with(density) { -50.dp.roundToPx() } }
                        ),
                exit = fadeOut(animationSpec = tween(300)),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
            ) {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (partnerData != null) {
                                AsyncImage(
                                    model = partnerData.logo,
                                    contentDescription = null,
                                    modifier =
                                        Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            if (partnerData != null) {
                                Text(
                                    text = partnerData.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            if (partnerData != null) {
                                onSharePartner(partnerData)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share"
                            )
                        }
                    },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
                        ),
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                )
            }

            FloatingActionButton(
                onClick = {
                    if (partnerData != null) {
                        onContactEmail(partnerData.contact_email)
                    }
                },
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Contact"
                )
            }
        }
    }
}
