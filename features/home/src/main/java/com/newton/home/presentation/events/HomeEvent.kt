package com.newton.home.presentation.events

sealed class HomeEvent {
    data class RSVPEvent(val eventId: Int) : HomeEvent()
}
