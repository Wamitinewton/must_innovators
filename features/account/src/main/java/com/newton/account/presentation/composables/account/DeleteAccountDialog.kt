package com.newton.account.presentation.composables.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*

@Composable
fun DeleteConsequenceCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation =
        CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 32.dp)
            )
        }
    }
}

@Composable
fun DeleteAccountConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    CustomDialog(
        title = "Confirm Account Deletion",
        isErrorDialog = true,
        onDismiss = onDismiss,
        confirmButtonText = "Yes, Delete My Account",
        onConfirm = onConfirm
    ) {
        Text(
            text = "This action is permanent and cannot be undone. Are you sure you want to delete your account?",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
