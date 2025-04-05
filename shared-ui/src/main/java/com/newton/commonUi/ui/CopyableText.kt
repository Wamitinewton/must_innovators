package com.newton.commonUi.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.core.utils.*
import kotlinx.coroutines.*

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
            color = textColor
                ?: if (highlightText) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                val success =
                    copyToClipboard(
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
