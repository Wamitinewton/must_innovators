package com.newton.admin.data.mappers

import com.newton.core.domain.models.admin_models.FeedbackData
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
            submissionTimestamp,
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

    fun List<FeedbackData>.toUserFeedbackListEntity(): List<UserFeedbackEntity> =
        map { it.toUserFeedbackEntity() }
    fun List<UserFeedbackEntity>.toDomainList()= map { it.toDomain() }
}