package com.newton.admin.presentation.home.events

sealed class AdminHomeEvent {
    data class Sheet(val shown: Boolean) : AdminHomeEvent()
}
