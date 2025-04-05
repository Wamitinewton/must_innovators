package com.newton.auth.presentation.login.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
import com.newton.commonUi.ui.*

@Composable
fun LoginForm(
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: String?,
    isEmailError: Boolean = false,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: String?,
    isPasswordError: Boolean = false
) {
    Column {
        val focusManager = LocalFocusManager.current
        AuthTextFields(
            onInputChanged = {
                onEmailChanged(it)
            },
            isError = isEmailError,
            errorMessage = emailError,
            inputText = email,
            label = "email",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
        PasswordTextInput(
            value = password,
            onValueChange = {
                onPasswordChanged(it)
            },
            label = "password",
            isError = isPasswordError,
            imeAction = ImeAction.Next
        )
    }
}
