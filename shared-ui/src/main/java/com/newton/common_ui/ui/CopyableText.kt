package com.newton.common_ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.core.utils.copyToClipboard
import kotlinx.coroutines.delay

/**
 * A reusable composable that displays text with a copy button
 *
 * @param text Text to display and copy
 * @param modifier Modifier for the component
 * @param highlightText Whether to highlight the text
 * @param showCopiedFeedback Whether to show copy confirmation
 * @param toastMessage Custom toast message
 * @param textColor Color for the text (defaults to appropriate color based on highlight)
 */

@Composable
fun CopyableText(
    modifier: Modifier = Modifier,
    text: String,
    highlightText: Boolean = false,
    showCopiedFeedback: Boolean = true,
    toastMessage: String = "Copied to clipboard",
    textColor: androidx.compose.ui.graphics.Color? = null
) {
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            delay(2000)
        copied = false
    }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (highlightText) FontWeight.Bold else FontWeight.Normal,
            color = textColor ?: if (highlightText) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                val success = copyToClipboard(
                    context = context,
                    text = text,
                    showToast = !showCopiedFeedback,
                    toastMessage = toastMessage
                )
                if (success && showCopiedFeedback) {
                    copied = true
                }
            }
        ) {
            AnimatedVisibility(
                visible = !copied,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy to clipboard",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = copied,
                enter = fadeIn(animationSpec = tween(200)),
                exit = fadeOut(animationSpec = tween(200))
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Copied",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}