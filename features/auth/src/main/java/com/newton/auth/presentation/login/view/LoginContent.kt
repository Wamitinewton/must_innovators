package com.newton.auth.presentation.login.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.login.event.LoginEvent
import com.newton.auth.presentation.login.state.LoginViewModelState
import com.newton.auth.presentation.utils.AuthHeader

@Composable
fun LoginContent(
    uiState: LoginViewModelState,
    onEvent: (LoginEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthHeader(
            onBackButtonClick = onBackClick,
            headerText = "Log In"
        )
    }
}