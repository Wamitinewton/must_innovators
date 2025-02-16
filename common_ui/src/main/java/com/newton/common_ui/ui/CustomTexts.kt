package com.newton.common_ui.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun CustomTextLabelSmall(
    modifier: Modifier = Modifier,
    bodyText: String,
    textColor: Color = Color.Unspecified
) {
    Text(
        modifier = modifier,
        text = bodyText,
        style = TextStyle(
            color = textColor,
            fontSize = 9.sp,
            fontWeight = FontWeight.W300,
            fontFamily = FontFamily.Cursive,
            lineHeight = 14.sp
        ),
        textAlign = TextAlign.Start
    )
}