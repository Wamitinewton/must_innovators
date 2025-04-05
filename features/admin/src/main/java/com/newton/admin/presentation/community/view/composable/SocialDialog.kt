package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.admin.*

@Composable
fun SocialDialog(
    social: Socials?,
    onDismiss: () -> Unit,
    onSave: (Socials) -> Unit
) {
    val isEditing = social != null

    var platform by remember { mutableStateOf(social?.platform ?: "") }
    var url by remember { mutableStateOf(social?.url ?: "https://") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEditing) "Edit Social link" else "Add New Social link") },
        text = {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = platform,
                    onValueChange = {
                        platform = it
                    },
                    label = { Text("Platform") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Social platform"
                        )
                    },
                    modifier =
                    Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                    },
                    label = { Text("Url") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "social link"
                        )
                    },
                    modifier =
                    Modifier
                        .fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        Socials(
                            platform = platform,
                            url = url
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
