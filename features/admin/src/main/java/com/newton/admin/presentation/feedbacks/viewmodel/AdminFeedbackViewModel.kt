package com.newton.admin.presentation.feedbacks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.AdminAction
import com.newton.admin.domain.models.enums.FeedbackCategory
import com.newton.admin.domain.models.enums.FeedbackPriority
import com.newton.admin.domain.models.enums.FeedbackStatus
import com.newton.admin.presentation.feedbacks.states.FeedbackState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class AdminFeedbackViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FeedbackState>(FeedbackState.Loading)
    val uiState: StateFlow<FeedbackState> = _uiState.asStateFlow()

    private val _feedbacks = MutableStateFlow<List<FeedBack>>(emptyList())
    val feedbacks: StateFlow<List<FeedBack>> = _feedbacks.asStateFlow()

    private val _selectedFilter = MutableStateFlow<FeedbackStatus?>(null)
    val selectedFilter: StateFlow<FeedbackStatus?> = _selectedFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadFeedbacks()
    }

    fun loadFeedbacks() {

        viewModelScope.launch {
            _uiState.value = FeedbackState.Loading
            delay(1500)
            val mockFeedbacks = generateMockFeedbacks(30)
            _feedbacks.value = mockFeedbacks
            _uiState.value = FeedbackState.Success
        }
    }

    fun updateFeedbackAction(feedbackId: String, action: AdminAction) {
        val currentFeedbacks = _feedbacks.value.toMutableList()
        val feedbackIndex = currentFeedbacks.indexOfFirst { it.id == feedbackId }

        if (feedbackIndex >= 0) {
            val feedback = currentFeedbacks[feedbackIndex]
            val updatedFeedback = when (action) {
                AdminAction.WRITE -> feedback.copy(status = FeedbackStatus.RESOLVED)
                AdminAction.REPLY -> feedback.copy(status = FeedbackStatus.RESOLVED)
                AdminAction.GRAMMAR_CHECK -> feedback.copy(hasGrammarIssues = !feedback.hasGrammarIssues)
            }

            currentFeedbacks[feedbackIndex] = updatedFeedback
            _feedbacks.value = currentFeedbacks
        }
    }

    fun setFilter(status: FeedbackStatus?) {
        _selectedFilter.value = status
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredFeedbacks(): List<FeedBack> {
        val currentFilter = _selectedFilter.value
        val query = _searchQuery.value.lowercase()

        return _feedbacks.value.filter { feedback ->
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