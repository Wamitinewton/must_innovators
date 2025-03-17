package com.newton.auth.presentation.reset_password.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.CustomButton
import com.newton.common_ui.ui.PasswordTextInput

@Composable
fun NewPasswordScreen(
    newPassword: String,
    confirmPassword: String,
    isLoading: Boolean,
    passwordError: String?,
    confirmPasswordError: String?,
    changePasswordError: String?,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSubmit: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(changePasswordError) {
        changePasswordError?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
        }
    }

    DefaultScaffold(
        snackbarHostState = snackbarHostState,
        isLoading = isLoading,
        showOrbitals = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create New Password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Please create a strong password for your account",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            PasswordTextInput(
                value = newPassword,
                onValueChange = onPasswordChanged,
                label = "New Password",
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError != null
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextInput(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChanged,
                label = "Confirm Password",
                modifier = Modifier.fillMaxWidth(),
                isError = confirmPasswordError != null
            )

            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                enabled = !isLoading,
                onClick = onSubmit,
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Text(
                        text = "Create Password",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}