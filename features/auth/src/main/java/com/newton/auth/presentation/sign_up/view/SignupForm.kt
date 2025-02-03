package com.newton.auth.presentation.sign_up.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.newton.auth.presentation.sign_up.event.SignupUiEvent
import com.newton.common_ui.ui.DefaultTextInput
import com.newton.common_ui.ui.PasswordTextInput

@Composable
fun SignupForm(
    firstName: String,
    onFirstnameChanged: (String) -> Unit,
    lastName: String,
    onLastnameChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: String?,
    isEmailError: Boolean = false,
    courseName: String,
    onCourseNameChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: String?,
    isPasswordError: Boolean = false,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
    confirmPwdError: String?,
    isConfirmPwdError: Boolean = false,
) {
    Column {
        val focusManager = LocalFocusManager.current
        DefaultTextInput(
            onInputChanged = {
                onFirstnameChanged(it)
            },
            inputText = firstName,
            label = "first name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
        DefaultTextInput(
            onInputChanged = {
                onLastnameChanged(it)
            },
            inputText = lastName,
            label = "last name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
        DefaultTextInput(
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
        DefaultTextInput(
            onInputChanged = {
                onCourseNameChanged(it)
            },

            inputText = courseName,
            label = "Course name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
        PasswordTextInput(
            onValueChange = {
                onPasswordChanged(it)
            },
            isError = isPasswordError,
            errorMessage = passwordError,
            value = password,
            label = "password",
            imeAction = ImeAction.Next
        )
        PasswordTextInput(
            onValueChange = {
                onConfirmPasswordChanged(it)
            },
            isError = isConfirmPwdError,
            errorMessage = confirmPwdError,
            value = confirmPassword,
            label = "confirm password",
            imeAction = ImeAction.Next
        )

    }
}