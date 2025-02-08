package com.newton.events.presentation.states

data class CategoryState(
    var selectedCategory: String = "All",
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
