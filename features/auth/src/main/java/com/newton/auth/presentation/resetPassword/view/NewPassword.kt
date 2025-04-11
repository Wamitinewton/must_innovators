/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.auth.presentation.resetPassword.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*

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
            modifier =
                Modifier
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
