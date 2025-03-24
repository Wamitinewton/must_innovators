package com.newton.communities.presentation.view.about_us

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.ErrorScreen
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.communities.presentation.state.ClubBioUiState
import com.newton.communities.presentation.view.about_us.composables.AboutSection
import com.newton.communities.presentation.view.about_us.composables.VisionAndMission

@Composable
fun ClubBioContent(
    uiState: ClubBioUiState,
    isAboutExpanded: Boolean,
    onReadMoreClick: () -> Unit,
) {
    when (uiState) {
        is ClubBioUiState.Loading -> {
            LoadingIndicator(text = "Loading club bio...")
        }
        is ClubBioUiState.Error -> {
            ErrorScreen(
                message = uiState.message,
                titleText = "Failed to load CLUB BIO",
                onRetry = {}
            )
        }
        is ClubBioUiState.Success -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AboutSection(
                    aboutText = uiState.data.about_us,
                    isExpanded = isAboutExpanded,
                    onReadMoreClick = onReadMoreClick
                )
                VisionAndMission(
                    vision = uiState.data.vision,
                    mission = uiState.data.mission
                )
            }
        }
    }
}