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
package com.newton.communities.presentation.view

import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import com.newton.commonUi.ui.*
import com.newton.communities.presentation.event.*
import com.newton.communities.presentation.state.*
import com.newton.communities.presentation.viewModel.*
import com.newton.network.domain.models.aboutUs.*

@Composable
fun CommunityContent(
    uiState: CommunitiesUiState,
    onCommunityDetailsClick: (Community) -> Unit,
    communitiesViewModel: CommunitiesViewModel
) {
    when (uiState) {
        is CommunitiesUiState.Loading -> {
            LoadingIndicator(
                text = "Loading Communities"
            )
        }

        is CommunitiesUiState.Success -> {
            val communities = uiState.communities

            if (communities.isEmpty()) {
                EmptyStateCard(
                    icon = Icons.Outlined.Groups,
                    title = "No Communities Found",
                    message = "There are no communities available at the moment. Please check back later.",
                    buttonText = "Refresh",
                    onActionClick = {
                        communitiesViewModel.onEvent(CommunityUiEvent.Action.RefreshCommunities)
                    }
                )
            } else {
                communities.forEach { community ->
                    CommunityCard(
                        community = community,
                        onSeeDetailsClick = {
                            onCommunityDetailsClick(community)
                        }
                    )
                }
            }
        }

        is CommunitiesUiState.Error -> {
            ErrorScreen(
                titleText = "Failed to load COMMUNITIES",
                message = uiState.message,
                onRetry = {
                    communitiesViewModel.onEvent(CommunityUiEvent.Action.RefreshCommunities)
                }
            )
        }


        CommunitiesUiState.Initial -> {}
    }
}
