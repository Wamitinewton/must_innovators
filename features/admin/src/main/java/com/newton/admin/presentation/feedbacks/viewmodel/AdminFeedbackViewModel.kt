package com.newton.admin.presentation.feedbacks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.presentation.feedbacks.events.FeedbackEvent
import com.newton.admin.presentation.feedbacks.states.FeedbackState
import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.repositories.AdminRepository
import com.newton.core.enums.AdminAction
import com.newton.core.enums.FeedbackCategory
import com.newton.core.enums.FeedbackPriority
import com.newton.core.enums.FeedbackStatus
import com.newton.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AdminFeedbackViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeedbackState())
    val uiState: StateFlow<FeedbackState> = _uiState.asStateFlow()

    init {
        loadFeedbacks()
    }

    fun handleEvent(event: FeedbackEvent) {
        when (event) {
            is FeedbackEvent.ErrorChange -> _uiState.update { it.copy(errorMessage = event.message) }
            is FeedbackEvent.FeedbacksChange -> _uiState.update { it.copy(feedbacks = event.feedback) }
            is FeedbackEvent.IsLoadingChange -> _uiState.update { it.copy(isLoading = event.isLoading) }
            is FeedbackEvent.IsSuccessChange -> _uiState.update { it.copy(isSuccess = event.isSuccess) }
            is FeedbackEvent.SearchQueryChange -> _uiState.update { it.copy(searchQuery = event.search) }
            is FeedbackEvent.SelectedFilterChange -> _uiState.update { it.copy(selectedFilter = event.filter) }
            FeedbackEvent.LoadFeedback -> loadFeedbacks()
        }
    }


    private fun loadFeedbacks() {
        viewModelScope.launch {
            adminRepository.getAllFeedbacks(
                isRefresh = true,
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _uiState.update { it.copy(errorMessage = result.message) }
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> result.data?.let { feedbackData ->
                        _uiState.update { it.copy(feedbacks = feedbackData,isSuccess = true) }
                    }
                }
            }
        }
    }


    fun updateFeedbackAction(feedbackId: Int, action: AdminAction) {
        val currentFeedbacks = _uiState.value.feedbacks.toMutableList()
        val feedbackIndex = currentFeedbacks.indexOfFirst { it.id == feedbackId }

        if (feedbackIndex >= 0) {
            val feedback = currentFeedbacks[feedbackIndex]
            val updatedFeedback = when (action) {
                AdminAction.WRITE -> feedback.copy(status = FeedbackStatus.RESOLVED)
                AdminAction.REPLY -> feedback.copy(status = FeedbackStatus.RESOLVED)
                AdminAction.GRAMMAR_CHECK -> feedback.copy(hasGrammarIssues = !feedback.hasGrammarIssues)
            }

            currentFeedbacks[feedbackIndex] = updatedFeedback
            _uiState.update { it.copy(feedbacks = currentFeedbacks) }
        }
    }


    fun getFilteredFeedbacks(): List<FeedbackData> {

        val currentFilter = _uiState.value.selectedFilter
        val query = _uiState.value.searchQuery.lowercase()

        return _uiState.value.feedbacks.filter { feedback ->
            val matchesFilter = currentFilter == null || feedback.status == currentFilter
            val matchesSearch = query.isEmpty() ||
                    feedback.userName.lowercase().contains(query) ||
                    feedback.content.lowercase().contains(query) ||
                    feedback.category.name.lowercase().replace("_", " ").contains(query)

            matchesFilter && matchesSearch
        }
    }
}