package com.newton.commonUi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import coil3.compose.*
import coil3.request.*

@Composable
fun CustomDynamicAsyncImage(
    imageUrl: Any?,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        AsyncImage(
            model =
            ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = placeholder,
            contentDescription = contentDescription,
            modifier = Modifier.matchParentSize(),
            error = placeholder,
            contentScale = contentScale,
            onSuccess = { isLoading = false },
            onError = { isLoading = false }
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
