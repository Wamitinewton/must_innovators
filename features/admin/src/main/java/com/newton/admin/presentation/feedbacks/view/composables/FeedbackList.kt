package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.AdminAction


@Composable
fun FeedbackList(
    feedbacks: List<FeedBack>,
    listState: LazyListState,
    onFeedbackClick: (String) -> Unit,
    onActionToggle: (String, AdminAction) -> Unit
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
            val animatedModifier = remember {
                Modifier.animateItem(
                    fadeInSpec = null, fadeOutSpec = null, placementSpec = spring<IntOffset>(
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
