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
