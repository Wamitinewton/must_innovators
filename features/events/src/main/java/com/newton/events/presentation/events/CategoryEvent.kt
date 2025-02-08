package com.newton.events.presentation.events

sealed class CategoryEvent{
    data class OnCategorySelected(val category: String) : CategoryEvent()
}