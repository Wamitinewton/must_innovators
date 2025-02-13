package com.newton.auth.presentation.login.event

sealed class GetUserDataEvent {
    data object GetUserEvent: GetUserDataEvent()
}