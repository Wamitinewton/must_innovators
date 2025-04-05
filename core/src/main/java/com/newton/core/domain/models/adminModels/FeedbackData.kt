package com.newton.core.domain.models.adminModels

import com.newton.core.enums.*

data class FeedbackData(
    val id: Int,
    val userId: Int,
    val userName: String,
    val userProfilePic: String,
    val userEmail: String,
    val content: String,
    val submittedAt: String,
    val status: FeedbackStatus,
    val priority: FeedbackPriority,
    val category: FeedbackCategory,
    val hasGrammarIssues: Boolean,
    val assignedTo: String? = null
)
