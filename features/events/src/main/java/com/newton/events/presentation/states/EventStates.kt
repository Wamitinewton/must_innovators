package com.newton.events.presentation.states

import com.newton.events.domain.models.Event

data class EventStates (
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String = "",
    val selectedCategory: String = "All",
    val categories: List<String> = listOf(
        "All",
        "Android",
        "Cyber Security",
        "Web development",
        "Robotics",
        "UI & UX",
        "Data Science",
        "AI and ML"
    )
)