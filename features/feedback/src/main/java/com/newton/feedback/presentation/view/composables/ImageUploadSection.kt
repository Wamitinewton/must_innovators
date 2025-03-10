package com.newton.feedback.presentation.view.composables

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun ImageUploadSection(
    images: List<Uri>,
    onAddImage: (Uri) -> Unit,
    onRemoveImages: (Uri) -> Unit,
    onReorderImages: (Int, Int) -> Unit,
    maxImages: Int
) {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(
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
            val message = if (availableSlots == 0) {
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
        ) {imageCount ->
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
        modifier = Modifier
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