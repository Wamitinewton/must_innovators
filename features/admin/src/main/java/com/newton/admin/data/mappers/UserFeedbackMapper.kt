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
package com.newton.admin.data.mappers

import com.newton.core.domain.models.adminModels.*
import com.newton.core.enums.*
import com.newton.database.entities.*

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

    private fun UserFeedbackResponse.toFeedbackData(): FeedbackData {
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

    fun List<UserFeedbackResponse>.toFeedbackData(): List<FeedbackData> =
        map { it.toFeedbackData() }

    fun List<FeedbackData>.toUserFeedbackListEntity(): List<UserFeedbackEntity> =
        map { it.toUserFeedbackEntity() }

    fun List<UserFeedbackEntity>.toDomainList() = map { it.toDomain() }
}
