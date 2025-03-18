package com.newton.admin.data.mappers

import com.newton.core.domain.models.admin_models.FeedbackData
import com.newton.core.domain.models.admin_models.UserFeedbackResponse
import com.newton.core.enums.FeedbackPriority
import com.newton.database.entities.UserFeedbackEntity

object UserFeedbackMapper {

    fun FeedbackData.toUserFeedbackEntity(): UserFeedbackEntity {
        return UserFeedbackEntity(
            id,
            userId,
            userName,
            userProfilePic,
            userEmail,
            content,
            submittedAt,
            status,
            priority,
            category,
            hasGrammarIssues,
            assignedTo
        )
    }

    fun UserFeedbackEntity.toDomain(): FeedbackData {
        return FeedbackData(
            id,
            userId,
            userName,
            userProfilePic,
            userEmail,
            content,
            submissionTimestamp,
            status,
            priority,
            category,
            hasGrammarIssues,
            assignedTo
        )
    }

    private fun UserFeedbackResponse.toFeedbackData():FeedbackData{
        return FeedbackData(
            id = id,
            userId = user,
            userName = user_name,
            userProfilePic = "",
            userEmail = email,
            content = comment,
            submittedAt = submitted_at,
            status = enumValueOf(status),
            priority = FeedbackPriority.LOW,
            category = enumValueOf(category),
            hasGrammarIssues = false
        )
    }

    fun List<UserFeedbackResponse>.toFeedbackData():List<FeedbackData> = map{it.toFeedbackData()}

    fun List<FeedbackData>.toUserFeedbackListEntity(): List<UserFeedbackEntity> =
        map { it.toUserFeedbackEntity() }
    fun List<UserFeedbackEntity>.toDomainList()= map { it.toDomain() }
}