package com.newton.meruinnovators.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object ThemeUtils {
    data class ThemeColorPair(
        val light: Color,
        val dark: Color
    )

    object AppColors {
        val Primary = Color(0xFF53B175)
        val PrimaryVariant = Color(0xFF429A5E)
        val Secondary = Color(0xFF03DAC5)
        val SecondaryVariant = Color(0xFF018786)

        val Background = ThemeColorPair(
            light = Color(0xFFF8F8F8),
            dark = Color(0xFF121212)
        )

        val Surface = ThemeColorPair(
            light = Color.White,
            dark = Color(0xFF1E1E1E)
        )

        val ElevatedSurface = ThemeColorPair(
            light = Color(0xFFFAFAFA),
            dark = Color(0xFF2C2C2C)
        )

        val Text = ThemeColorPair(
            light = Color(0xFF121212),
            dark = Color(0xFFF1F1F1)
        )

        val InvertedText = ThemeColorPair(
            dark = Color(0xFF121212),
            light = Color(0xFFF1F1F1)
        )

        val SecondaryText = ThemeColorPair(
            light = Color(0xFF555555),
            dark = Color(0xFFBBBBBB)
        )

        val Divider = ThemeColorPair(
            light = Color(0xFFE0E0E0),
            dark = Color(0xFF2A2A2A)
        )

        val Error = Color(0xFFB00020)
        val Success = Color(0xFF43A047)

        val Accent1 = Color(0xFF6200EE)
        val Accent2 = Color(0xFFFF8F00)
    }

    private val DarkColorPalette = darkColorScheme(
        primary = AppColors.Primary,
        primaryContainer = AppColors.PrimaryVariant,
        secondary = AppColors.Secondary,
        secondaryContainer = AppColors.SecondaryVariant,
        background = AppColors.Background.dark,
        surface = AppColors.Surface.dark,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = AppColors.Text.dark,
        onSurface = AppColors.Text.dark,
        error = AppColors.Error,
        onError = Color.White
    )

    private val LightColorPalette = lightColorScheme(
        primary = AppColors.Primary,
        primaryContainer = AppColors.PrimaryVariant,
        secondary = AppColors.Secondary,
        secondaryContainer = AppColors.SecondaryVariant,
        background = AppColors.Background.light,
        surface = AppColors.Surface.light,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = AppColors.Text.light,
        onSurface = AppColors.Text.light,
        error = AppColors.Error,
        onError = Color.White
    )

    private val LocalThemeMode = staticCompositionLocalOf { false }

    @Composable
    fun ThemeColorPair.themed(): Color {
        return if (LocalThemeMode.current) dark else light
    }

    @Composable
    fun MeruinnovatorsTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colorScheme = if (darkTheme)
            DarkColorPalette
        else
            LightColorPalette

        CompositionLocalProvider(LocalThemeMode provides darkTheme) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )
        }
    }
}

