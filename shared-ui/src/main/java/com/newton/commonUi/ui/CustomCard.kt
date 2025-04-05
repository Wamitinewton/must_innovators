package com.newton.commonUi.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    border: BorderStroke? = null,
    content:
        @Composable()
        (ColumnScope.() -> Unit)
) {
    Card(
        modifier =
        Modifier
            .padding(8.dp)
            .then(modifier),
        content = content,
        colors =
        CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        ),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = border
    )
}

@Composable
fun CustomDialog(
    title: String,
    isErrorDialog: Boolean = false,
    onDismiss: () -> Unit,
    confirmButtonText: String,
    dismissButtonText: String = "Cancel",
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                color = if (isErrorDialog) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        },
        text = {
            content()
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = if (isErrorDialog) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissButtonText)
            }
        }
    )
}
