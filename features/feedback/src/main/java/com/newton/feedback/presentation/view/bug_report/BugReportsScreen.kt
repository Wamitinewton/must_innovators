package com.newton.feedback.presentation.view.bug_report

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.MultilineInputField
import com.newton.common_ui.ui.SubmitButton
import com.newton.feedback.presentation.view.composables.ImageUploadSection
import com.newton.feedback.presentation.view.composables.SuccessAnimation
import com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BugReportsScreen(
    viewModel: UserFeedbackViewModel,
    onBackPress: () -> Unit
) {
    val bugDescription by viewModel.bugDescription.collectAsState()
    val bugImages by viewModel.bugImages.collectAsState()
    val isSubmitting by viewModel.isSubmitting.collectAsState()
    val isSubmitSuccess by viewModel.isSubmitSuccess.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report a Bug") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Go Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MultilineInputField(
                    value = bugDescription,
                    onValueChange = { viewModel.updateBugDescription(it) },
                    label = "Describe the bug",
                    placeholder = "What happened? what did you expect?",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    maxLines = 5,
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Add Screenshots (optional)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ImageUploadSection(
                        images = bugImages,
                        onAddImage = { viewModel.addBugImage(it) },
                        onRemoveImages = { viewModel.removeBugImage(it) },
                        onReorderImages = viewModel::reorderImages,
                        maxImages = 3
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SubmitButton(
                    text = "Submit Bug Report",
                    isSubmitting = isSubmitting,
                    enabled = bugDescription.isNotEmpty(),
                    onClick = { viewModel.submitBugReport() }
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