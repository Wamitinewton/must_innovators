package com.newton.core.domain.models.admin

import com.newton.core.enums.FeedbackCategory
import com.newton.core.enums.FeedbackPriority
import com.newton.core.enums.FeedbackStatus

data class FeedBack(
    val id: String,
    val userId: String,
    val userName: String,
    val userProfilePic: String,
    val userEmail: String,
    val content: String,
    val submissionTimestamp: Long,
    val status: FeedbackStatus,
    val priority: FeedbackPriority,
    val category: FeedbackCategory,
    val hasGrammarIssues: Boolean,
    val assignedTo: String? = null
)
