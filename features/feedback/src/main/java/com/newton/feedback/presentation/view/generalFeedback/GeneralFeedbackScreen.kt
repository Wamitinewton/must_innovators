package com.newton.feedback.presentation.view.generalFeedback

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.feedback.presentation.view.composables.*
import com.newton.feedback.presentation.viewmodel.*

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
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
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
                        text = "How would you rate our app?",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    RatingBarInput(
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
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    maxLines = 5
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
