package com.newton.feedback.presentation.view.general_feedback

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.MultilineInputField
import com.newton.common_ui.ui.RatingBar
import com.newton.common_ui.ui.SubmitButton
import com.newton.feedback.presentation.view.composables.SuccessAnimation
import com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralFeedbackScreen(
    viewModel: UserFeedbackViewModel,
    onBackPress: () -> Unit
) {
    val generalFeedback by viewModel.generalFeedback.collectAsState()
    val rating by viewModel.rating.collectAsState()
    val isSubmitting by viewModel.isSubmitting.collectAsState()
    val isSubmitSuccess by viewModel.isSubmitSuccess.collectAsState()
    val scrollState = rememberScrollState()

    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = { Text("General Feedback") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
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
                        text = "How would you rate our app?",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    RatingBar(
                        currentRating = rating,
                        onRatingChanged = { viewModel.updateRating(it) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                MultilineInputField(
                    value = generalFeedback,
                    onValueChange = { viewModel.updateGeneralFeedback(it) },
                    label = "Your feedback",
                    placeholder = "What do you like? What could be improved?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    maxLines = 5,
                )

                Spacer(modifier = Modifier.height(16.dp))

                SubmitButton(
                    text = "Submit Feedback",
                    isSubmitting = isSubmitting,
                    enabled = generalFeedback.isNotEmpty() && rating > 0,
                    onClick = { viewModel.submitGeneralFeedback() }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            AnimatedVisibility(
                visible = isSubmitSuccess,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SuccessAnimation()
            }
        }
    }
}