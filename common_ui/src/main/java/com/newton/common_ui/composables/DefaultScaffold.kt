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
 * A reusable composable that provides a consistent space-themed background with animated
 * orbital patterns and stars that properly uses Material Theme colors.
 *
 * @param modifier Modifier to be applied to the container
 * @param snackbarHostState Optional SnackbarHostState for displaying snackbars
 * @param topBar Optional composable for displaying a top app bar
 * @param showOrbitals Whether to show the space-themed animated background
 * @param isLoading Whether to show a loading dialog
 * @param gradientColors List of colors to use for the gradient background (defaults to theme colors)
 * @param content The content to display on top of the gradient background
 */
@Composable
fun DefaultScaffold(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState? = null,
    topBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    showOrbitals: Boolean = true,
    isLoading: Boolean = false,
    gradientColors: List<Color> = getDefaultGradientColors(),
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            snackbarHostState?.let { SnackbarHost(hostState = it) }
        },
        floatingActionButton = floatingActionButton,
        topBar = topBar
    ) { padding ->
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
            OrbitalsBackground(
                primaryColor = MaterialTheme.colorScheme.primary,
                secondaryColor = MaterialTheme.colorScheme.secondary,
                tertiaryColor = MaterialTheme.colorScheme.tertiary,
                primaryAlpha = 0.35f,
                secondaryAlpha = 0.25f,
                tertiaryAlpha = 0.3f,
                backgroundAlpha = 1f,
            )
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

/**
 * Provides default gradient colors based on the current Material Theme
 * Creates a subtle gradient using surface variants from the theme
 */
@Composable
private fun getDefaultGradientColors(): List<Color> {
    val surface = MaterialTheme.colorScheme.surface
    val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    return listOf(
        surface,
        surface.copy(alpha = 0.95f),
//        surfaceVariant.copy(alpha = 0.9f)
    )
}