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
package com.newton.feedback.presentation.view.composables

import android.net.*
import android.widget.*
import androidx.activity.compose.*
import androidx.activity.result.contract.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*

@Composable
fun ImageUploadSection(
    images: List<Uri>,
    onAddImage: (Uri) -> Unit,
    onRemoveImages: (Uri) -> Unit,
    onReorderImages: (Int, Int) -> Unit,
    maxImages: Int
) {
    val context = LocalContext.current
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents()
        ) { uris ->
            val availableSlots = maxImages - images.size
            val newUris = uris.filter { it !in images }

            if (newUris.isEmpty()) {
                Toast.makeText(context, "Cannot select same image twice", Toast.LENGTH_SHORT).show()
                return@rememberLauncherForActivityResult
            }

            val urisToAdd = newUris.take(availableSlots)
            if (urisToAdd.size < uris.size) {
                val skippedCount = uris.size - urisToAdd.size
                val message =
                    if (availableSlots == 0) {
                        "Maximum $maxImages images reached"
                    } else {
                        "$skippedCount duplicate images skipped"
                    }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            urisToAdd.forEach(onAddImage)
        }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AnimatedContent(
            targetState = images.size,
            transitionSpec = {
                fadeIn() + slideInVertically() togetherWith
                    fadeOut() + slideOutVertically()
            },
            label = "imageCount"
        ) { imageCount ->
            Text(
                text = "$imageCount of $maxImages images",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (images.isEmpty()) {
                EmptyImagePlaceholder(
                    isEnabled = images.size < maxImages,
                    onClick = { galleryLauncher.launch("image/*") }
                )
            } else {
                ReorderableImageRow(
                    images = images,
                    onRemove = onRemoveImages,
                    onReorder = onReorderImages,
                    isAddEnabled = images.size < maxImages,
                    onAdd = { galleryLauncher.launch("image/*") }
                )
            }
        }
    }
}

@Composable
private fun EmptyImagePlaceholder(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                enabled = isEnabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Images",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Add Screenshots",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
