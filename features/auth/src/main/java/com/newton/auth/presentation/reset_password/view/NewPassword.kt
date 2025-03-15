package com.newton.auth.presentation.reset_password.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.common_ui.ui.PasswordTextInput


@Composable
fun NewPasswordScreen(
    newPassword: String,
    confirmPassword: String,
    isLoading: Boolean,
    passwordError: String?,
    confirmPasswordError: String?,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSubmit: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
            label = "Password",
            isError = passwordError != null,
            errorMessage = passwordError,
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextInput(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            label = "Password",
            isError = confirmPasswordError != null,
            errorMessage = confirmPasswordError,
        )


    }
}