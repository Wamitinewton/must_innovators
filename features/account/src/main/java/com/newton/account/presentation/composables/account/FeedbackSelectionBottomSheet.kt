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
package com.newton.account.presentation.composables.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.*

@Composable
fun FeedbackSelectionBottomSheet(
    onSelectBugReport: () -> Unit,
    onSelectGeneralFeedback: () -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier =
        Modifier
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
