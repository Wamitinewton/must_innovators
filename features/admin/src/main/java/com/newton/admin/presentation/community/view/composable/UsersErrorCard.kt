package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.community.events.*
import com.newton.commonUi.ui.*

@Composable
fun ErrorCard(
    message: String?,
    onRetry: (CommunityEvent) -> Unit
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message ?: "Failed to load user data",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onErrorContainer,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            onClick = {
                onRetry.invoke(CommunityEvent.LoadUsers(true))
            },
            modifier = Modifier.clip(CircleShape)
        ) {
            Text("Retry")
        }
    }
}
