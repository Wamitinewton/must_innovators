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

    private val _uiState = MutableStateFlow<ExecutiveUiState>(ExecutiveUiState.Loading)
    val uiState: StateFlow<ExecutiveUiState> = _uiState.asStateFlow()

    init {
        loadExecutives()
    }

    private fun loadExecutives() {
        viewModelScope.launch {
            _uiState.value = ExecutiveUiState.Loading

            repository.getExecutives().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = ExecutiveUiState.Success(
                            executives = result.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = ExecutiveUiState.Error(
                            message = result.message ?: "Unknown error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        if (result.isLoading) {
                            _uiState.value = ExecutiveUiState.Loading
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun retryLoadExecutives() {
        loadExecutives()
    }

    fun clearError() {
        if (_uiState.value is ExecutiveUiState.Error) {
            loadExecutives()
        }
    }
}