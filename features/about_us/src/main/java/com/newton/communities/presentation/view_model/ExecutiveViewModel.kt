package com.newton.communities.presentation.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.newton.core.domain.repositories.ExecutiveRepository
import com.newton.communities.presentation.state.ExecutiveUiState
import com.newton.core.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@HiltViewModel
class ExecutiveViewModel @Inject constructor(
    private val repository: ExecutiveRepository
) : ViewModel() {

    // Private mutable state flow to track UI state internally
    private val _uiState = MutableStateFlow(ExecutiveUiState())

    // Public immutable state flow exposed to the UI
    val uiState: StateFlow<ExecutiveUiState> = _uiState.asStateFlow()

    init {
        // Load executives when ViewModel is created
        loadExecutives()
    }


    private fun loadExecutives() {
        viewModelScope.launch {
            _uiState.value = ExecutiveUiState(isLoading = true)

            repository.getExecutives().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = ExecutiveUiState(
                            communities = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = ExecutiveUiState(
                            errorMessage = result.message,
                            isLoading = false,
                            communities = _uiState.value.communities
                        )
                    }

                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = result.isLoading,
                            communities = _uiState.value.communities,
                            errorMessage = null
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    fun retryLoadExecutives() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
        loadExecutives()
    }


    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}