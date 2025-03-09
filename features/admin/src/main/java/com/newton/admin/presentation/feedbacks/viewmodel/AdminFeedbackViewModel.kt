package com.newton.admin.presentation.feedbacks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.AdminAction
import com.newton.admin.domain.models.enums.FeedbackCategory
import com.newton.admin.domain.models.enums.FeedbackPriority
import com.newton.admin.domain.models.enums.FeedbackStatus
import com.newton.admin.domain.repository.AdminRepository
import com.newton.admin.presentation.feedbacks.events.FeedbackEvent
import com.newton.admin.presentation.feedbacks.states.FeedbackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AdminFeedbackViewModel @Inject constructor(
    private val adminRepository: AdminRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(FeedbackState())
    val uiState: StateFlow<FeedbackState> = _uiState.asStateFlow()
    init {
        loadFeedbacks()
    }

    fun handleEvent(event:FeedbackEvent){
        when(event){
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
            _uiState.update { it.copy(isLoading = true) }
            delay(1500)
            val mockFeedbacks = generateMockFeedbacks(30)
            _uiState.update { it.copy(feedbacks = mockFeedbacks) }
            _uiState.update { it.copy(isLoading = false) }
            _uiState.update { it.copy(isSuccess = true) }
        }
    }

    fun updateFeedbackAction(feedbackId: String, action: AdminAction) {
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

    fun setFilter(status: FeedbackStatus?) {
        _uiState.update { it.copy(selectedFilter = status) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun getFilteredFeedbacks(): List<FeedBack> {

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

    private fun generateMockFeedbacks(count: Int): List<FeedBack> {
        val random = Random(System.currentTimeMillis())

        val names = listOf(
            "Emma Thompson", "Liam Chen", "Olivia Johnson", "Noah Patel",
            "Sophia Rodriguez", "Jackson Kim", "Ava Nguyen", "Lucas Gupta",
            "Isabella Williams", "Aiden Garcia", "Mia Martinez", "Ethan Lee"
        )

        val domains = listOf("gmail.com", "outlook.com", "yahoo.com", "hotmail.com", "company.com")

        val feedbackTexts = listOf(
            "The app crashes every time I try to upload a profile picture. Could you fix this issue?",
            "I love the new dashboard design, but the analytics graphs are not displaying correctly on mobile devices.",
            "Is there a way to customize notification sounds? I often miss important alerts.",
            "The dark mode feature is amazing! However, some text becomes unreadable against dark backgrounds.",
            "I've been charged twice for my subscription this month. Please help me resolve this issue.",
            "Your app is running very slowly on my device after the latest update. Please optimize performance.",
            "Could you add a feature to schedule posts in advance? That would be really helpful for my workflow.",
            "The search functionality doesn't return relevant results. It needs improvement.",
            "I'm having trouble connecting my social media accounts. The integration seems broken.",
            "The password reset email never arrives when I request it. This is a critical issue for me.",
            "Your customer support team has been excellent! Thank you for resolving my issue so quickly.",
            "The new file sharing option is exactly what I needed. Great job on the implementation!",
            "I've noticed some grammatical errors in the help documentation. Would you like me to send details?",
            "The app keeps logging me out randomly. It's quite frustrating when I'm in the middle of work.",
            "Could you make the text size adjustable? The current font is too small for me to read comfortably."
        )

        return List(count) { index ->
            val name = names[random.nextInt(names.size)]
            val email = name.lowercase().replace(" ", ".") + "@" + domains[random.nextInt(domains.size)]

            FeedBack(
                id = "fb-${index + 1000}",
                userId = "user-${index + 100}",
                userName = name,
                userProfilePic = "https://randomuser.me/api/portraits/${if (random.nextBoolean()) "women" else "men"}/${random.nextInt(99) + 1}.jpg",
                userEmail = email,
                content = feedbackTexts[random.nextInt(feedbackTexts.size)],
                submissionTimestamp = System.currentTimeMillis() - random.nextLong(1000L * 60 * 60 * 24 * 30), // Up to 30 days ago
                status = FeedbackStatus.entries[random.nextInt(FeedbackStatus.entries.size)],
                priority = FeedbackPriority.entries[random.nextInt(FeedbackPriority.entries.size)],
                category = FeedbackCategory.entries[random.nextInt(FeedbackCategory.entries.size)],
                hasGrammarIssues = random.nextBoolean(),
                assignedTo = if (random.nextBoolean()) "admin${random.nextInt(5) + 1}@company.com" else null
            )
        }
    }
}