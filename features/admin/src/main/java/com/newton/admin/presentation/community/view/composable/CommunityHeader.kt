package com.newton.admin.presentation.community.view.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*

@Composable
fun CommunityHeader(
    communityName: String,
    isRecruiting: Boolean,
    isEditing: Boolean,
    onNameChange: (String) -> Unit,
    onRecruitingChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (isEditing) {
                OutlinedTextField(
                    value = communityName,
                    onValueChange = onNameChange,
                    label = { Text("Community Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = communityName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier =
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(if (isRecruiting) Color.Green else Color.Red)
                )

                Text(
                    text = if (isRecruiting) "Recruiting New Members" else "Not Recruiting",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )

                if (isEditing) {
                    Spacer(modifier = Modifier.weight(1f))

                    Switch(
                        checked = isRecruiting,
                        onCheckedChange = onRecruitingChange,
                        colors =
                        SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                            uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    }
}
