package com.newton.account.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.account.presentation.events.TestimonialsNavigationEvent
import com.newton.account.presentation.events.TestimonialsUiEvent
import com.newton.account.presentation.states.TestimonialsUiState
import com.newton.account.presentation.viewmodel.TestimonialsViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.MultilineInputField
import com.newton.common_ui.ui.RatingBarInput
import com.newton.common_ui.ui.SubmitButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTestimonial(
    viewModel: TestimonialsViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit,
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
                },
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
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
                        onRatingChanged = { viewModel.handleEvent(TestimonialsUiEvent.RatingChanged(it)) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MultilineInputField(
                    value = content,
                    onValueChange = { viewModel.handleEvent(TestimonialsUiEvent.ContentChanged(it)) },
                    label = "Your Testimonial",
                    placeholder = "Share your experience...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    maxLines = 5,
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
