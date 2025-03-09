package com.newton.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.newton.core.enums.FeedbackCategory
import com.newton.core.enums.FeedbackPriority
import com.newton.core.enums.FeedbackStatus

@Entity(tableName = "user_feedbacks")
data class UserFeedbackEntity (
    @PrimaryKey
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