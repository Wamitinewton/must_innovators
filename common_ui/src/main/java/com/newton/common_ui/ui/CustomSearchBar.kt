package com.newton.common_ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.custom_animations.SearchBarTransformation
import com.newton.common_ui.composables.animation.custom_animations.SearchBarTransformation.normalizeScrollOffset

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    scrollOffset: Float,
    onClearClick: () -> Unit = { onSearchQueryChanged("") },
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {}
) {
    val density = LocalDensity.current
    var boxWidth by remember { mutableIntStateOf(0) }
    var boxHeight by remember { mutableIntStateOf(0) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val progress by remember(scrollOffset, boxHeight) {
        derivedStateOf {
            val maxOffset = with(density) { 56.dp.toPx() }
            scrollOffset.normalizeScrollOffset(maxOffset)
        }
    }

    val scale by animateFloatAsState(
        targetValue = SearchBarTransformation.calculateScale(progress),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "ScaleAnimation"
    )

    val searchBarAlpha by animateFloatAsState(
        targetValue = SearchBarTransformation.calculateSearchBarAlpha(progress),
        animationSpec = tween(durationMillis = 300),
        label = "SearchBarAlphaAnimation"
    )

    val iconAlpha by animateFloatAsState(
        targetValue = SearchBarTransformation.calculateIconAlpha(progress),
        animationSpec = tween(durationMillis = 300),
        label = "IconAlphaAnimation"
    )

    val (xOffset, yOffset) = remember(progress, boxWidth, boxHeight) {
        SearchBarTransformation.calculatePositionOffset(
            progress = progress,
            maxWidth = boxWidth.toFloat(),
            maxHeight = boxHeight.toFloat()
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .onGloballyPositioned { coordinates ->
                boxWidth = coordinates.size.width
                boxHeight = coordinates.size.height
            }
    ) {
        AnimatedVisibility(
            visible = !active && searchQuery.isEmpty(),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            IconButton(
                onClick = {
                    onActiveChange(true)
                    focusRequester.requestFocus()
                },
                modifier = Modifier
                    .alpha(iconAlpha)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = xOffset
                    translationY = yOffset
                    alpha = searchBarAlpha
                }
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable(onClick = {
                    onActiveChange(true)
                    focusRequester.requestFocus()
                })
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClick()
                            focusManager.clearFocus()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            onActiveChange(focusState.isFocused)
                        },
                    decorationBox = { innerTextField ->
                        Box {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search events...",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = onClearClick,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Clear search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}