package com.newton.meruinnovators.ui.theme

import androidx.compose.material3.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.R

// set Fonts
val AlegreyaFontFamily =
    FontFamily(
        Font(R.font.gilroybold, FontWeight.Bold),
        Font(R.font.gilroysemibold, FontWeight.SemiBold)
    )

val AlegreyaSansFontFamily =
    FontFamily(
        Font(R.font.gilroyregular, FontWeight.Normal),
        Font(R.font.gilroymedium, FontWeight.Medium)
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        bodyLarge =
        TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        )
        /* Other default text styles to override
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
         */
    )
