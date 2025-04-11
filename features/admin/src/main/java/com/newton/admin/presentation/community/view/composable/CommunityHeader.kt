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
