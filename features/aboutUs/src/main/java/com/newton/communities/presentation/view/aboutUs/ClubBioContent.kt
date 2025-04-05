package com.newton.communities.presentation.view.aboutUs

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.communities.presentation.state.*
import com.newton.communities.presentation.view.aboutUs.composables.*

@Composable
fun ClubBioContent(
    uiState: ClubBioUiState,
    isAboutExpanded: Boolean,
    onReadMoreClick: () -> Unit
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
