package com.newton.common_ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.newton.common_ui.composables.animation.custom_animations.OrbitalsBackground
import com.newton.common_ui.ui.LoadingDialog

/**
 * A reusable composable that provides a consistent gradient background with optional
 * OrbitalsBackground animation for screens throughout the app.
 *
 * @param modifier Modifier to be applied to the container
 * @param snackbarHostState Optional SnackbarHostState for displaying snackbars
 * @param topBar Optional composable for displaying a top app bar
 * @param showOrbitals Whether to show the OrbitalsBackground animation
 * @param isLoading Whether to show a loading dialog
 * @param gradientColors List of colors to use for the gradient background (defaults to theme colors)
 * @param content The content to display on top of the gradient background
 */

@Composable
fun DefaultScaffold(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState? = null,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    showOrbitals: Boolean = true,
    isLoading: Boolean = false,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.surfaceDim,
        MaterialTheme.colorScheme.surfaceBright
    ),
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        snackbarHost = {
            snackbarHostState?.let { SnackbarHost(hostState = it) }
        },
        bottomBar = bottomBar,
        topBar = topBar

    ) { padding ->
        Box(modifier = Modifier.fillMaxSize())

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = gradientColors
                    )
                )
        )

        if (showOrbitals) {
            OrbitalsBackground()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            content = content
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LoadingDialog()
        }
    }
}