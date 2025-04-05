package com.newton.commonUi.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.R
import com.newton.commonUi.ui.*

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
            Image(painter = painterResource(R.drawable.oopsy), contentDescription = null)
            Spacer(Modifier.height(12.dp))
            Text(
                errorMessage,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(12.dp))
            if (showButton && onClick != null) {
                CustomElevatedButton(
                    onClick = onClick,
                    content = {
                        Text(
                            buttonText,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }
    }
}
