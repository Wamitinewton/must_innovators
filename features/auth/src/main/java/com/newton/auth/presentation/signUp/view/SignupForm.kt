package com.newton.auth.presentation.signUp.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
import com.newton.commonUi.ui.*

@Composable
fun SignupForm(
    username: String,
    onUsernameChanged: (String) -> Unit,
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
    isConfirmPwdError: Boolean = false
) {
    Column {
        val focusManager = LocalFocusManager.current
        AuthTextFields(
            onInputChanged = {
                onFirstnameChanged(it)
            },
            inputText = firstName,
            label = "first name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
        AuthTextFields(
            onInputChanged = {
                onLastnameChanged(it)
            },
            inputText = lastName,
            label = "last name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
        AuthTextFields(
            onInputChanged = {
                onUsernameChanged(it)
            },
            inputText = username,
            label = "Username",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
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
        AuthTextFields(
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
            value = password,
            onValueChange = {
                onPasswordChanged(it)
            },
            label = "password",
            isError = isPasswordError,
            imeAction = ImeAction.Next
        )
        PasswordTextInput(
            value = confirmPassword,
            onValueChange = {
                onConfirmPasswordChanged(it)
            },
            label = "confirm password",
            isError = isConfirmPwdError,
            imeAction = ImeAction.Next
        )
    }
}
