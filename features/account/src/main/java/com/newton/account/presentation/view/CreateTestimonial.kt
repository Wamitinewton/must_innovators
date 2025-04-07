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
package com.newton.account.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.*
import com.newton.account.presentation.events.*
import com.newton.account.presentation.states.*
import com.newton.account.presentation.viewmodel.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTestimonial(
    viewModel: TestimonialsViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val rating by viewModel.rating.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                TestimonialsNavigationEvent.NavigateToHome -> {
                    onNavigateToHome()
                }
            }
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is TestimonialsUiState.Error -> {
                launch {
                    snackbarHostState.showSnackbar(
                        message = (uiState as TestimonialsUiState.Error).message,
                        duration = SnackbarDuration.Short
                    )
                    viewModel.handleEvent(TestimonialsUiEvent.ClearError)
                }
            }

            is TestimonialsUiState.Success -> {
                launch {
                    snackbarHostState.showSnackbar(
                        message = (uiState as TestimonialsUiState.Success).message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            else -> Unit
        }
    }

    DefaultScaffold(
        isLoading = uiState is TestimonialsUiState.Loading,
        snackbarHostState = snackbarHostState,
        topBar = {
            TopAppBar(
                title = { Text("Submit a Testimonial") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "How would you rate our service?",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    RatingBarInput(
                        currentRating = rating,
                        onRatingChanged = {
                            viewModel.handleEvent(
                                TestimonialsUiEvent.RatingChanged(
                                    it
                                )
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MultilineInputField(
                    value = content,
                    onValueChange = { viewModel.handleEvent(TestimonialsUiEvent.ContentChanged(it)) },
                    label = "Your Testimonial",
                    placeholder = "Share your experience...",
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                SubmitButton(
                    text = "Submit Testimonial",
                    isSubmitting = uiState is TestimonialsUiState.Loading,
                    enabled = content.isNotEmpty() && rating > 0,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.handleEvent(TestimonialsUiEvent.Submit)
                        }
                    }
                )
            }
        }
    }
}
