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
package com.newton.commonUi.theme

import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.hilt.navigation.compose.*
import com.newton.core.enums.*
import com.newton.meruinnovators.ui.theme.*

object Theme {
    data class ThemeColorPair(
        val light: Color,
        val dark: Color
    )

    object ThemeColors {
        val Primary = Color(0xFF3B82F6)
        val PrimaryVariant = Color(0xFF2563EB)

        val Secondary = Color(0xFF8B5CF6)
        val SecondaryVariant = Color(0xFF7C3AED)

        val Background = ThemeColorPair(
            light = Color(0xFFF0F5F3),
            dark = Color(0xFF1A2238)
        )

        val Surface = ThemeColorPair(
            light = Color(0xFFF0F5F3),
            dark = Color(0xFF1E293B)
        )


        val ElevatedSurface = ThemeColorPair(
            light = Color(0xFFF1F5F9),
            dark = Color(0xFF334155)
        )

        val Text = ThemeColorPair(
            light = Color(0xFF0F172A),
            dark = Color(0xFFF8FAFC)
        )

        val InvertedText = ThemeColorPair(
            light = Color(0xFFF8FAFC),
            dark = Color(0xFF0F172A)
        )

        val SecondaryText = ThemeColorPair(
            light = Color(0xFF64748B),
            dark = Color(0xFFCBD5E1)
        )

        val Divider = ThemeColorPair(
            light = Color(0xFFE2E8F0),
            dark = Color(0xFF334155)
        )

        val Error = Color(0xFFEF4444)
        val Success = Color(0xFF22C55E)
        val Warning = Color(0xFFF59E0B)
        val Info = Color(0xFF0EA5E9)

        val Accent1 = Color(0xFF06B6D4)
        val Accent2 = Color(0xFFFACC15)
        val Accent3 = Color(0xFFA855F7)
        val Accent4 = Color(0xFF64748B)
    }


    private val DarkColorPalette =
        darkColorScheme(
            primary = ThemeColors.Primary,
            primaryContainer = ThemeColors.PrimaryVariant,
            secondary = ThemeColors.Secondary,
            secondaryContainer = ThemeColors.SecondaryVariant,
            background = ThemeColors.Background.dark,
            surface = ThemeColors.Surface.dark,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = ThemeColors.Text.dark,
            onSurface = ThemeColors.Text.dark,
            error = ThemeColors.Error,
            onError = Color.White,
            tertiary = ThemeColors.Accent1,
            tertiaryContainer = ThemeColors.Accent3
        )

    private val LightColorPalette =
        lightColorScheme(
            primary = ThemeColors.Primary,
            primaryContainer = ThemeColors.PrimaryVariant,
            secondary = ThemeColors.Secondary,
            secondaryContainer = ThemeColors.SecondaryVariant,
            background = ThemeColors.Background.light,
            surface = ThemeColors.Surface.light,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = ThemeColors.Text.light,
            onSurface = ThemeColors.Text.light,
            error = ThemeColors.Error,
            onError = Color.White,
            tertiary = ThemeColors.Accent1,
            tertiaryContainer = ThemeColors.Accent3
        )

    private val LocalThemeMode = staticCompositionLocalOf { false }

    @Composable
    fun MeruinnovatorsTheme(
        content: @Composable () -> Unit,
        themeViewModel: com.newton.sharedprefs.viewModel.ThemeViewModel = hiltViewModel()
    ) {
        val themeState by themeViewModel.themeState.collectAsState()
        val systemInDarkTheme = isSystemInDarkTheme()

        val darkTheme = when (themeState.themeMode) {
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
            ThemeMode.SYSTEM -> systemInDarkTheme
        }
        val colorScheme = remember(darkTheme) {
            if (darkTheme) DarkColorPalette else LightColorPalette
        }

        CompositionLocalProvider(LocalThemeMode provides darkTheme) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )
        }
    }

    /**
     * Extension function to get appropriate color based on current theme
     */
    @Composable
    fun ThemeColorPair.current(): Color {
        val darkTheme = LocalThemeMode.current
        return if (darkTheme) dark else light
    }
}
