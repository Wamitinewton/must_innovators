package com.newton.events.presentation.states

data class SearchEventState(
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val suggestions: List<String> = emptyList(),
    val searchHistory: List<String> = emptyList()
)
