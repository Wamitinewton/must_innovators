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
package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.adminModels.*
import com.newton.core.enums.*

@Composable
fun FeedbackList(
    feedbacks: List<FeedbackData>,
    listState: LazyListState,
    onFeedbackClick: (Int) -> Unit,
    onActionToggle: (Int, AdminAction) -> Unit
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = feedbacks,
            key = { _, feedback -> feedback.id }
        ) { index, feedback ->
            val animatedModifier =
                remember {
                    Modifier.animateItem(
                        fadeInSpec = null,
                        fadeOutSpec = null,
                        placementSpec =
                        spring<IntOffset>(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                }

            FeedbackCard(
                feedback = feedback,
                onCardClick = { onFeedbackClick(feedback.id) },
                onActionToggle = onActionToggle,
                modifier = animatedModifier,
                index = index
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp)) // For FAB space
        }
    }
}
