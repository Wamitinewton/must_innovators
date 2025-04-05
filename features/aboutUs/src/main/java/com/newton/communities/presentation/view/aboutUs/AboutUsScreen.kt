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
package com.newton.communities.presentation.view.aboutUs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.*
import com.newton.commonUi.composables.*
import com.newton.communities.presentation.events.*
import com.newton.communities.presentation.view.aboutUs.composables.*
import com.newton.communities.presentation.viewModel.*
import com.newton.core.domain.models.aboutUs.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    onCommunityDetailsClick: (Community) -> Unit,
    communitiesViewModel: CommunitiesViewModel,
    executiveViewModel: ExecutiveViewModel,
    clubBioViewModel: ClubBioViewModel
) {
    val uiState by communitiesViewModel.uiState.collectAsStateWithLifecycle()
    val clubBioUiState by clubBioViewModel.uiState.collectAsState()
    val executiveUiState by executiveViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val communityName = "Meru Science Innovators Club"
    var isAboutExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        communitiesViewModel.uiEvents.collectLatest { event ->
            when (event) {
                is CommunityUiEvent.Effect.ShowSnackbar -> {
                    val result =
                        snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = "Retry",
                            duration = SnackbarDuration.Long
                        )

                    if (result == SnackbarResult.ActionPerformed) {
                        communitiesViewModel.onEvent(CommunityUiEvent.Action.RefreshCommunities)
                    }
                }
            }
        }
    }

    DefaultScaffold(
        snackbarHostState = snackBarHostState,
        showOrbitals = true,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = communityName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ClubBioContent(
                    onReadMoreClick = { isAboutExpanded = !isAboutExpanded },
                    uiState = clubBioUiState,
                    isAboutExpanded = isAboutExpanded
                )
            }

            item {
                SectionHeading(
                    title = "Our Communities",
                    icon = Icons.Filled.Groups
                )
            }

            item {
                CommunityContent(
                    uiState = uiState,
                    onCommunityDetailsClick = onCommunityDetailsClick,
                    communitiesViewModel = communitiesViewModel
                )
            }

            item {
                SectionHeading(
                    title = "Executive Team",
                    icon = Icons.Filled.Person
                )
            }

            item {
                ExecutivesSection(
                    uiState = executiveUiState
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
