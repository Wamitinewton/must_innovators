package com.newton.events.presentation.events

sealed class SearchEvent {
    data class Search(val query: String): SearchEvent()
    data class SuggestionSelected(val suggestion: String): SearchEvent()
    data object ClearSearch: SearchEvent()
    data object ClearHistory: SearchEvent()
}