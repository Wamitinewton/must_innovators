/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.admin.presentation.feedbacks.viewmodel

import androidx.lifecycle.*
import com.newton.admin.presentation.feedbacks.events.*
import com.newton.admin.presentation.feedbacks.states.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.domain.repositories.*
import com.newton.core.enums.*
import com.newton.core.utils.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class AdminFeedbackViewModel
@Inject
constructor(
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
                isRefresh = true
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> _uiState.update { it.copy(errorMessage = result.message) }
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success ->
                        result.data?.let { feedbackData ->
                            _uiState.update { it.copy(feedbacks = feedbackData, isSuccess = true) }
                        }
                }
            }
        }
    }

    fun updateFeedbackAction(
        feedbackId: Int,
        action: AdminAction
    ) {
        val currentFeedbacks = _uiState.value.feedbacks.toMutableList()
        val feedbackIndex = currentFeedbacks.indexOfFirst { it.id == feedbackId }

        if (feedbackIndex >= 0) {
            val feedback = currentFeedbacks[feedbackIndex]
            val updatedFeedback =
                when (action) {
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
            val matchesSearch =
                query.isEmpty() ||
                    feedback.userName.lowercase().contains(query) ||
                    feedback.content.lowercase().contains(query) ||
                    feedback.category.name.lowercase().replace("_", " ").contains(query)

            matchesFilter && matchesSearch
        }
    }
}
