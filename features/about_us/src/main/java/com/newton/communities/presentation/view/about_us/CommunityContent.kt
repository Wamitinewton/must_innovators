package com.newton.communities.presentation.view.about_us

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.EmptyStateCard
import com.newton.common_ui.ui.ErrorBanner
import com.newton.communities.presentation.events.CommunityUiEvent
import com.newton.communities.presentation.state.CommunitiesUiState
import com.newton.communities.presentation.view.about_us.composables.CommunitiesList
import com.newton.communities.presentation.view.about_us.composables.CommunityCard
import com.newton.communities.presentation.view.about_us.composables.CommunityCardShimmer
import com.newton.communities.presentation.view_model.CommunitiesViewModel
import com.newton.core.domain.models.about_us.Community


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
                ErrorBanner(
                    errorMessage = uiState.message,
                    onRetryClick = {
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