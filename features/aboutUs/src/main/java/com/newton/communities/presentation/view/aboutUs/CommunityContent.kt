package com.newton.communities.presentation.view.aboutUs

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.communities.presentation.events.*
import com.newton.communities.presentation.state.*
import com.newton.communities.presentation.view.aboutUs.composables.*
import com.newton.communities.presentation.viewModel.*
import com.newton.core.domain.models.aboutUs.*

@Composable
fun CommunityContent(
    uiState: CommunitiesUiState,
    onCommunityDetailsClick: (Community) -> Unit,
    communitiesViewModel: CommunitiesViewModel
) {
    if (uiState !is CommunitiesUiState.Loading || (uiState as? CommunitiesUiState.Loading)?.isRefreshing == true) {
        Text(
            text = "Explore our specialized communities that focus on different aspects of technology",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }

    when (uiState) {
        is CommunitiesUiState.Loading -> {
            if (!uiState.isRefreshing) {
                repeat(1) {
                    CommunityCardShimmer()
                }
            }
        }

        is CommunitiesUiState.Content -> {
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
            val communities = uiState.communities

            if (communities.isEmpty()) {
                EmptyStateCard(
                    icon = Icons.Outlined.ErrorOutline,
                    title = "Something Went Wrong",
                    message = uiState.message,
                    buttonText = "Try Again",
                    onActionClick = {
                        communitiesViewModel.onEvent(CommunityUiEvent.Action.RefreshCommunities)
                    },
                    iconTint = MaterialTheme.colorScheme.error,
                    buttonColor = MaterialTheme.colorScheme.error
                )
            } else {
                ErrorScreen(
                    titleText = "Failed to load COMMUNITIES",
                    message = uiState.message,
                    onRetry = {
                        communitiesViewModel.onEvent(CommunityUiEvent.Action.RefreshCommunities)
                    }
                )

                CommunitiesList(
                    communities = communities,
                    onCommunityClick = onCommunityDetailsClick
                )
            }
        }
    }
}
