package com.newton.events.presentation.events

sealed class EventsEvent {
    data class SearchInputChanged(val searchInput: String) : EventsEvent()
    data class OnCategorySelected(val category: String) : EventsEvent()
}