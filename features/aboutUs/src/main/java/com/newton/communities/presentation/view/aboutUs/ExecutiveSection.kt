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

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.communities.presentation.state.*
import com.newton.communities.presentation.view.aboutUs.composables.*
import com.newton.core.domain.models.aboutUs.*

@Composable
fun ExecutivesSection(uiState: ExecutiveUiState) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Meet the talented individuals who lead our technology initiatives",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        when (uiState) {
            is ExecutiveUiState.Loading -> {
                ExecutiveCardShimmer()
            }

            is ExecutiveUiState.Success -> {
                val executives = uiState.executives
                ExecutiveListSection(executives = executives)
            }

            is ExecutiveUiState.Error -> {
                val errorMessage = uiState.message
                ErrorScreen(
                    titleText = "Failed to load COMMUNITY EXECUTIVES",
                    message = errorMessage,
                    onRetry = { }
                )
            }
        }
    }
}

@Composable
fun ExecutiveListSection(executives: List<Executive>) {
    var visibleItems by remember { mutableStateOf(false) }

    androidx.compose.runtime.LaunchedEffect(executives) {
        visibleItems = true
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(executives) { index, executive ->
            AnimatedVisibility(
                visible = visibleItems,
                enter =
                fadeIn(
                    animationSpec =
                    tween(
                        durationMillis = 300,
                        delayMillis = 100 * index
                    )
                )
            ) {
                ExecutiveCard(
                    executive = executive,
                    modifier = Modifier
                )
            }
        }
    }
}
