package com.newton.onBoarding.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*

@Composable
fun AuthBottomSheetContent(
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DisplaySmallText(
            text = "Welcome to Meru Innovators Club!",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        BodyMediumText(
            text = "Join us to innovate and lead the future.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        AuthButton(
            text = "Log In",
            icon = Icons.Default.Key,
            isPrimary = true,
            onClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthButton(
            text = "Sign Up",
            icon = Icons.Default.Person,
            isPrimary = false,
            onClick = onSignupClick
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}
