package com.newton.auth.presentation.login.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import com.newton.common_ui.ui.AuthTextFields
import com.newton.common_ui.ui.PasswordTextInput

@Composable
fun LoginForm(
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: String?,
    isEmailError: Boolean = false,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: String?,
    isPasswordError: Boolean = false,
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