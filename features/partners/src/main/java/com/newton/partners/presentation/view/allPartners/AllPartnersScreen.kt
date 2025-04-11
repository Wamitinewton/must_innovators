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
package com.newton.partners.presentation.view.allPartners

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import com.newton.commonUi.ui.*
import com.newton.network.domain.models.homeModels.*
import com.newton.partners.presentation.state.*
import com.newton.partners.presentation.viewModel.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPartnersListScreen(
    partnersViewModel: PartnersViewModel,
    onNavigateBack: () -> Unit,
    onPartnerClick: (PartnersData) -> Unit
) {
    val partnersState by partnersViewModel.partnersState.collectAsState()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val scrolled = remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 0 } }

    val backgroundColor = MaterialTheme.colorScheme.surface

    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Partners Network",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = scrolled.value,
                enter = fadeIn(animationSpec = spring()),
                exit = fadeOut(animationSpec = spring())
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = "Scroll to top"
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            when (partnersState) {
                is PartnersUiState.Loading -> {
                    LoadingIndicator(text = "Loading PARTNERS")
                }

                is PartnersUiState.Error -> {
                    ErrorScreen(
                        titleText = "Failed to load partners",
                        message = (partnersState as PartnersUiState.Error).message,
                        onRetry = { partnersViewModel.refreshPartners() }
                    )
                }

                is PartnersUiState.Empty -> {
                    EmptyStateCard(
                        icon = Icons.Rounded.Handshake,
                        title = "No Partners Found",
                        message = "There are no partners available at this time. Check back later.",
                        buttonText = "Refresh",
                        onActionClick = { partnersViewModel.refreshPartners() }
                    )
                }

                is PartnersUiState.Success -> {
                    val partners = (partnersState as PartnersUiState.Success).partners
                    PartnersListContent(
                        partners = partners,
                        lazyListState = lazyListState,
                        onPartnerClick = onPartnerClick
                    )
                }
            }
        }
    }
}

