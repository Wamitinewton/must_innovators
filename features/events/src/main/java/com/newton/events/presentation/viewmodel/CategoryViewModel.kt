package com.newton.events.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.newton.events.presentation.events.CategoryEvent
import com.newton.events.presentation.states.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//@HiltViewModel
class CategoryViewModel : ViewModel() {
    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> = _state.asStateFlow()

    fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.OnCategorySelected -> {
                _state.value = _state.value.copy(selectedCategory = event.category)
            }
        }
    }
}