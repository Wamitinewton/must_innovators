package com.newton.admin.domain.models

import com.newton.admin.domain.models.enums.FeedbackCategory
import com.newton.admin.domain.models.enums.FeedbackPriority
import com.newton.admin.domain.models.enums.FeedbackStatus

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
