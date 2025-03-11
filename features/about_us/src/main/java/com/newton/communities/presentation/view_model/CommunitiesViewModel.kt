package com.newton.communities.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.core.domain.repositories.CommunityRepository
import com.newton.communities.presentation.events.CommunityEvent
import com.newton.communities.presentation.events.UiEvent
import com.newton.communities.presentation.state.CommunitiesUiState
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunitiesViewModel @Inject constructor(
    private val repository: CommunityRepository
): ViewModel() {

    private val _state = MutableStateFlow(CommunitiesUiState())
    val communityState: StateFlow<CommunitiesUiState> = _state.asStateFlow()

    /**
     * One Time Events
     */
    private val _eventFlow = MutableSharedFlow<CommunityEvent>()
    val eventFlow: SharedFlow<CommunityEvent> = _eventFlow.asSharedFlow()

    init {
        loadCommunities()
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.RefreshCommunities -> {
                refreshCommunities()
            }
            is UiEvent.DeleteCommunity -> {
                deleteCommunity(event.communityId)
            }
            is UiEvent.ClearAllData -> {
                clearAllData()
            }
            is UiEvent.DismissError -> {
                _state.update { it.copy(errorMessage = null) }
            }
        }
    }

    private fun refreshCommunities() {
        loadCommunities(forceRefresh = true)
    }

    private fun loadCommunities(forceRefresh: Boolean = false) {
        viewModelScope.launch {

                repository.getCommunities(isRefreshing = forceRefresh).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    errorMessage = result.message,
                                    communities = result.data ?: it.communities
                                )
                            }
                            _eventFlow.emit(CommunityEvent.ShowSnackbar(
                                result.message ?: "An unexpected error occurred"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = result.isLoading,
                                    isRefreshing = forceRefresh
                                )
                            }
                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    communities = result.data ?: emptyList(),
                                    isLoading = false,
                                    isRefreshing = false,
                                    errorMessage = null
                                )
                            }
                            _eventFlow.emit(CommunityEvent.ShowSnackbar(
                                "Communities loaded successfully"
                            ))
                        }
                    }
                }
            }
        }


    private fun deleteCommunity(communityId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = repository.deleteCommunity(communityId)) {
                is Resource.Success -> {
                    // Remove from state
                    _state.update { currentState ->
                        currentState.copy(
                            communities = currentState.communities.filter { it.id != communityId },
                            isLoading = false
                        )
                    }
                    _eventFlow.emit(CommunityEvent.ShowSnackbar("Community deleted successfully"))
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    _eventFlow.emit(CommunityEvent.ShowSnackbar(
                        result.message ?: "Failed to delete community"
                    ))
                }
                else -> {
                    // Loading handled above
                }
            }
        }
    }


    private fun clearAllData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = repository.clearAllData()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            communities = emptyList(),
                            isLoading = false
                        )
                    }
                    _eventFlow.emit(CommunityEvent.ShowSnackbar("All data cleared successfully"))
                }
                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    _eventFlow.emit(CommunityEvent.ShowSnackbar(
                        result.message ?: "Failed to clear data"
                    ))
                }
                else -> {
                }
            }
        }
    }

}

