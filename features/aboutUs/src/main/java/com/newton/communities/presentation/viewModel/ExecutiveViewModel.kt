package com.newton.communities.presentation.viewModel

import androidx.lifecycle.*
import com.newton.communities.presentation.state.*
import com.newton.core.domain.repositories.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ExecutiveViewModel
@Inject
constructor(
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
                        _uiState.value =
                            ExecutiveUiState.Success(
                                executives = result.data ?: emptyList()
                            )
                    }

                    is Resource.Error -> {
                        _uiState.value =
                            ExecutiveUiState.Error(
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
