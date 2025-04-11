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
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import coil3.request.*
import com.newton.commonUi.ui.*
import kotlin.math.*

@Composable
fun ReorderableImageRow(
    images: List<Uri>,
    onRemove: (Uri) -> Unit,
    onReorder: (Int, Int) -> Unit,
    isAddEnabled: Boolean,
    onAdd: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    var draggedItemIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var currentHoveredIndex by remember { mutableStateOf<Int?>(null) }

    val itemWidth = 100.dp
    val itemSpacing = 8.dp

    LazyRow(
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(itemSpacing),
        modifier =
        Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec =
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        itemsIndexed(
            items = images,
            key = { index, uri -> "$uri-$index" }
        ) { index, uri ->
            val isDragged = index == draggedItemIndex
            val offsetX = if (isDragged) dragOffset.x.roundToInt() else 0

            Box(
                modifier =
                Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        if (isDragged) {
                            scaleX = 1.1f
                            scaleY = 1.1f
                        }
                    }
                    .shadow(
                        elevation = if (isDragged) 8.dp else 0.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .offset { IntOffset(offsetX, 0) }
                    .zIndex(if (isDragged) 1f else 0f)
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                draggedItemIndex = index
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffset += dragAmount
                                // Calculate which item we're hovering over
                                val totalDragX = dragOffset.x
                                val estimatedPosition =
                                    (totalDragX / (itemWidth.toPx() + itemSpacing.toPx())).roundToInt()
                                val targetIndex =
                                    (index + estimatedPosition).coerceIn(0, images.lastIndex)

                                if (targetIndex != currentHoveredIndex) {
                                    currentHoveredIndex = targetIndex
                                }
                            },
                            onDragEnd = {
                                currentHoveredIndex?.let { targetValue ->
                                    if (targetValue != index) {
                                        onReorder(index, targetValue)
                                    }
                                }

                                draggedItemIndex = null
                                dragOffset = Offset.Zero
                                currentHoveredIndex = null
                            },
                            onDragCancel = {
                                draggedItemIndex = null
                                dragOffset = Offset.Zero
                                currentHoveredIndex = null
                            }
                        )
                    }
            ) {
                ImagePreviewItem(
                    uri = uri,
                    onRemove = { onRemove(uri) },
                    isDragging = isDragged
                )
            }
        }

        if (isAddEnabled) {
            item {
                AddItemButton(
                    text = "Add Image",
                    onClick = onAdd
                )
            }
        }
    }
}

@Composable
fun ImagePreviewItem(
    modifier: Modifier = Modifier,
    uri: Uri,
    onRemove: () -> Unit,
    isDragging: Boolean = false
) {
    Box(
        modifier =
        modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isDragging) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                }
            )
    ) {
        AsyncImage(
            model =
            ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .build(),
            contentDescription = "Image Preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = onRemove,
            modifier =
            Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove Image",
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
