package com.newton.account.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun FeedbackSelectionBottomSheet(
    onSelectBugReport: () -> Unit,
    onSelectGeneralFeedback: () -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "How would you like us to improve?",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        FeedbackOptionCard(
            title = "Report a bug",
            description = "Help us fix issues by reporting bugs you encounter",
            icon = Icons.Filled.BugReport,
            onClick = {
                scope.launch {
                    onDismiss()
                    onSelectBugReport()
                }
            }
        )

        FeedbackOptionCard(
            title = "General Feedback",
            description = "Share your thoughts about the app and suggestions",
            icon = Icons.Filled.Feedback,
            onClick = {
                scope.launch {
                    onDismiss()
                    onSelectGeneralFeedback()
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}