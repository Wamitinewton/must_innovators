package com.newton.feedback.presentation.view.composables

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.newton.common_ui.ui.AddItemButton
import kotlin.math.roundToInt


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
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        itemsIndexed(
            items = images,
            key = { index, uri -> "$uri-$index" }
        ){ index, uri ->
            val isDragged = index == draggedItemIndex
            val offsetX = if (isDragged) dragOffset.x.roundToInt() else 0

            Box(
                modifier = Modifier
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
                                val estimatedPosition = (totalDragX / (itemWidth.toPx() + itemSpacing.toPx())).roundToInt()
                                val targetIndex = (index + estimatedPosition).coerceIn(0, images.lastIndex)

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
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isDragging)
                MaterialTheme.colorScheme.primaryContainer
                else
                MaterialTheme.colorScheme.surfaceVariant
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .build(),
            contentDescription = "Image Preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier
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