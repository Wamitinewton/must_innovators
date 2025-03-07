package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.domain.models.Socials
import com.newton.common_ui.ui.CustomButton

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = platform,
                    onValueChange = {
                        platform = it
                    }, label = { Text("Platform") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Social platform") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = url,
                    onValueChange = {
                        url = it
                    },label = { Text("Url") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = "social link") },
                    modifier = Modifier
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