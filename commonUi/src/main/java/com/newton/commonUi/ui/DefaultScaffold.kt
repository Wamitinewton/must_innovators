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
package com.newton.commonUi.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import com.newton.commonUi.animations.*

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
    bottomBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            snackbarHostState?.let { SnackbarHost(hostState = it) }
        },
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar,
        topBar = topBar
    ) { padding ->
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                    Brush.verticalGradient(
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
                showStars = true
            )
        }

        Box(
            modifier =
            Modifier
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
        surface.copy(alpha = 0.95f)
//        surfaceVariant.copy(alpha = 0.9f)
    )
}
