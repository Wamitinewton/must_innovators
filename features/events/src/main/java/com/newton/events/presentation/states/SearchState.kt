package com.newton.events.presentation.states

import com.newton.events.domain.models.Event

data class SearchState(
    val isLoading: Boolean = false,
    val searchInput: String = "",
    val searchResponse: List<Event> = emptyList()
)