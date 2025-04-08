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
package com.newton.testimonials.presentation.view.allTestimonials

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.testimonials.presentation.state.*
import com.newton.testimonials.presentation.viewModel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTestimonialsScreen(
    onBackPressed: () -> Unit,
    testimonialViewModel: GetTestimonialsViewModel
) {
    val uiState by testimonialViewModel.uiState.collectAsState()

    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Testimonials",
                            style = MaterialTheme.typography.titleLarge
                        )
                        AnimatedContent(
                            targetState = when (uiState) {
                                is GetTestimonialsUiState.Success ->
                                    "${(uiState as GetTestimonialsUiState.Success).testimonials.size} reviews from our community"
                                is GetTestimonialsUiState.Loading -> "Loading reviews..."
                                else -> "Community reviews"
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
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {},
                        enabled = uiState is GetTestimonialsUiState.Success
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = if (uiState is GetTestimonialsUiState.Success) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (uiState) {
                is GetTestimonialsUiState.Error -> {
                    val errorMessage = (uiState as GetTestimonialsUiState.Error).message
                    ErrorScreen(
                        titleText = "Failed To Load Testimonials",
                        message = errorMessage,
                        onRetry = { testimonialViewModel.retryLoadingTestimonials() }
                    )
                }
                is GetTestimonialsUiState.Initial -> {
                    Box(modifier = Modifier.fillMaxSize())
                }
                is GetTestimonialsUiState.Loading -> {
                    LoadingIndicator(text = "Loading Testimonials")
                }
                is GetTestimonialsUiState.Success -> {
                    val testimonials = (uiState as GetTestimonialsUiState.Success).testimonials
                    AllTestimonialsSection(testimonials = testimonials)
                }
            }
        }
    }
}
