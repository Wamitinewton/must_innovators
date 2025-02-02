package com.newton.auth.presentation.sign_up.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
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
    userName: String,
    onUsernameChanged: (String) -> Unit,
    registrationNo: String,
    onRegistrationNoChanged: (String) -> Unit,
    courseName: String,
    onCourseNameChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
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
            inputText = email,
            label = "email",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }

        )
        DefaultTextInput(
            onInputChanged = {
                onUsernameChanged(it)
            },
            inputText = userName,
            label = "user name",
            onSubmitted = {},
            imeAction = ImeAction.Next,
            onImeAction = { focusManager.moveFocus(FocusDirection.Next) }
        )
        DefaultTextInput(
            onInputChanged = {
                onRegistrationNoChanged(it)
            },
            inputText = registrationNo,
            label = "registration no",
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
            value = password,
            label = "password",
            imeAction = ImeAction.Next
        )
        PasswordTextInput(
            onValueChange = {
                onConfirmPasswordChanged(it)
            },
            value = confirmPassword,
            label = "confirm password",
            imeAction = ImeAction.Next
        )

    }
}