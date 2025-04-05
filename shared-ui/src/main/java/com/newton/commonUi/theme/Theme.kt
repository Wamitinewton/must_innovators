package com.newton.commonUi.theme

import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import com.newton.meruinnovators.ui.theme.*

object Theme {
    data class ThemeColorPair(
        val light: Color,
        val dark: Color
    )

    object ThemeColors {
        val Primary = Color(0xFF53B175)
        val PrimaryVariant = Color(0xFF429A5E)
        val Secondary = Color(0xFF03DAC5)
        val SecondaryVariant = Color(0xFF018786)

        val Background =
            ThemeColorPair(
                light = Color(0xFFF8F8F8),
                dark = Color(0xFF121212)
            )

        val Surface =
            ThemeColorPair(
                light = Color.White,
                dark = Color(0xFF1E1E1E)
            )

        val ElevatedSurface =
            ThemeColorPair(
                light = Color(0xFFFAFAFA),
                dark = Color(0xFF2C2C2C)
            )

        val Text =
            ThemeColorPair(
                light = Color(0xFF121212),
                dark = Color(0xFFF1F1F1)
            )

        val InvertedText =
            ThemeColorPair(
                dark = Color(0xFF121212),
                light = Color(0xFFF1F1F1)
            )

        val SecondaryText =
            ThemeColorPair(
                light = Color(0xFF555555),
                dark = Color(0xFFBBBBBB)
            )

        val Divider =
            ThemeColorPair(
                light = Color(0xFFE0E0E0),
                dark = Color(0xFF2A2A2A)
            )

        val Error = Color(0xFFB00020)
        val Success = Color(0xFF43A047)

        val Accent1 = Color(0xFF6200EE)
        val Accent2 = Color(0xFFFF8F00)
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
            onSecondary = Color.Black,
            onBackground = ThemeColors.Text.dark,
            onSurface = ThemeColors.Text.dark,
            error = ThemeColors.Error,
            onError = Color.White
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
            onSecondary = Color.Black,
            onBackground = ThemeColors.Text.light,
            onSurface = ThemeColors.Text.light,
            error = ThemeColors.Error,
            onError = Color.White
        )

    private val LocalThemeMode = staticCompositionLocalOf { false }

    @Composable
    fun MeruinnovatorsTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colorScheme =
            if (darkTheme) {
                DarkColorPalette
            } else {
                LightColorPalette
            }

        CompositionLocalProvider(LocalThemeMode provides darkTheme) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )
        }
    }
}
