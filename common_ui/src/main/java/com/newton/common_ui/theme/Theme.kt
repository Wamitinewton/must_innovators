package com.newton.meruinnovators.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.newton.common_ui.theme.blackColor
import com.newton.common_ui.theme.lightGrayBackgroundColor

object ThemeUtils {
    data class ThemeColorPair(
        val light: Color,
        val dark: Color
    )


    object AppColors {
        val Background = ThemeColorPair(
            light = lightGrayBackgroundColor,
            dark = blackColor
        )

        val Surface = ThemeColorPair(
            light = Color.White,
            dark = Color(0xFF1E1E1E)
        )

        val Text = ThemeColorPair(
            light = Color(0xFF181725),
            dark = Color.White
        )

        val InvertText = ThemeColorPair(
            dark = Color(0xFF181725),
            light = Color.White
        )

        val SecondaryText = ThemeColorPair(
            light = Color(0xFF7C7C7C),
            dark = Color(0xFFB0B0B0)
        )

        val Divider = ThemeColorPair(
            light = Color(0xFFE2E2E2),
            dark = Color(0xFF2A2A2A)
        )

        val Primary = Color(0xff53B175)
        val Purple700 = Color(0xFF3700B3)
        val Teal200 = Color(0xFF03DAC5)
    }

    private val DarkColorPalette = darkColorScheme(
        primary = AppColors.Primary,
        secondary = AppColors.Teal200,
        background = AppColors.Background.dark,
        surface = AppColors.Surface.dark,
        onBackground = AppColors.Text.dark,
        onSurface = AppColors.Text.dark
    )

    private val LightColorPalette = darkColorScheme(
        primary = AppColors.Primary,
        secondary = AppColors.Teal200,
        background = AppColors.Background.light,
        surface = AppColors.Surface.light,
        onBackground = AppColors.Text.light,
        onSurface = AppColors.Text.light
    )

    @Composable
    fun ThemeColorPair.themed(): Color {
        return if (isSystemInDarkTheme()) dark else light
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

//        val view = LocalView.current
//        if (!view.isInEditMode) {
//            SideEffect {
//                val window = (view.context as Activity).window
//                window.statusBarColor = colorScheme.primary.toArgb()
//                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//            }
//        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
