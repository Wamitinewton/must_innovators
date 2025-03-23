package com.newton.events.presentation.events

sealed class UserTicketsEvent {
    data class Refresh(val email: String): UserTicketsEvent()
    data object ClearErrors: UserTicketsEvent()
    data class Initialize(val email: String): UserTicketsEvent()
}