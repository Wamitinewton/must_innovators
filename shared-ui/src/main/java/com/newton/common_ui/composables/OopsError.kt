package com.newton.common_ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.newton.common_ui.R
import com.newton.common_ui.ui.CustomElevatedButton

@Composable
fun OopsError(
    modifier: Modifier = Modifier,
    errorMessage: String,
    buttonText: String = "Try Again",
    showButton: Boolean = false,
    onClick: (() -> Unit)? = null,
    imageHeight: Dp = 200.dp
) {
    require(!showButton || onClick != null) {
        "onClick must be provided when showButton is true"
    }
    Box(
        modifier = Modifier
            .padding(12.dp)
            .then(modifier)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter =  painterResource(R.drawable.oopsy), contentDescription = null )
            Spacer(Modifier.height(12.dp))
            Text(
                errorMessage,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
            )
            Spacer(Modifier.height(12.dp))
            if (showButton) {
                if (onClick != null) {
                    CustomElevatedButton(
                        onClick = onClick,
                        content = { Text(buttonText, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onSurface) }
                    )
                }
            }
        }
    }
}