package com.newton.common_ui.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.meruinnovators.ui.theme.AlegreyaSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextInput(
    onInputChanged: (String) -> Unit,
    inputText: String,
    hint: String,
    errorMessage: String? = null,
    onSubmitted: (() -> Unit)? = null
) {
    TextField(
        value = inputText,
        onValueChange = onInputChanged,
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = AlegreyaSansFontFamily,
                    color = Color(0xFFBEC2C2)
                )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFFBEC2C2),
            unfocusedIndicatorColor = Color(0xFFBEC2C2)
        )
    )
}