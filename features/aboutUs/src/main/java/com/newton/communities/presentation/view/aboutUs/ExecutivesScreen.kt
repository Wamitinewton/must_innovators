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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.newton.commonUi.ui.CustomScaffold
import com.newton.communities.presentation.state.ExecutiveUiState
import com.newton.communities.presentation.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExecutivesScreen(
    viewModel: ExecutiveViewModel,
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CustomScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Executive Members",
                            style = MaterialTheme.typography.titleLarge
                        )

                        AnimatedContent(
                            targetState = when (uiState) {
                                is ExecutiveUiState.Loading -> "Loading Executives..."
                                is ExecutiveUiState.Success ->
                                    "${(uiState as ExecutiveUiState.Success).executives.size} executives"
                                else -> "Community executives"
                            },
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "subtitle"
                        ) { subtitle ->
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
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
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ExecutivesSection(uiState = uiState)
        }
    }
}
