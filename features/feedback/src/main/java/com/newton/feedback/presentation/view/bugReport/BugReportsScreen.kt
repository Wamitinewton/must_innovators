package com.newton.feedback.presentation.view.bugReport

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.newton.commonUi.ui.*
import com.newton.feedback.presentation.view.composables.*
import com.newton.feedback.presentation.viewmodel.*

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
                colors =
                TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier =
                Modifier
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
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp),
                    maxLines = 5
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
