package com.newton.on_boarding.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.AuthButton
import com.newton.common_ui.ui.BodyMediumText
import com.newton.common_ui.ui.DisplaySmallText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthBottomSheetContent(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    Column(
        modifier = Modifier
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