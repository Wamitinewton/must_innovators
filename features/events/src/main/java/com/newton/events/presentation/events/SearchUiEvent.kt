package com.newton.events.presentation.events

sealed class SearchUiEvent {
    data class Search(val query: String) : SearchUiEvent()

    data object ClearSearch : SearchUiEvent()

    data object RetrySearch : SearchUiEvent()

    data object ExecuteSearch : SearchUiEvent()
}