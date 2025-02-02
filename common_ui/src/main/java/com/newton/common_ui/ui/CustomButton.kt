package com.newton.common_ui.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.meruinnovators.ui.theme.AlegreyaFontFamily
import com.newton.meruinnovators.ui.theme.ThemeUtils
import com.newton.meruinnovators.ui.theme.ThemeUtils.themed
import com.newton.meruinnovators.ui.theme.lightGrayColor

@Composable
fun CustomButton(
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = AlegreyaFontFamily,
                fontWeight = FontWeight(500),
                color = lightGrayColor
            )
        )
    }
}