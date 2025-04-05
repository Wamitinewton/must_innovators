package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.feedbacks.events.*
import com.newton.core.enums.*

@Composable
fun FilterSection(
    selectedFilter: FeedbackStatus?,
    onFilterSelected: (FeedbackStatus) -> Unit,
    onEvent: (FeedbackEvent) -> Unit
) {
    val filters =
        remember {
            listOf(
                null to "All",
                FeedbackStatus.PENDING to "Pending",
                FeedbackStatus.IN_PROGRESS to "In Progress",
                FeedbackStatus.RESOLVED to "Completed"
            )
        }

    ScrollableTabRow(
        selectedTabIndex =
        filters.indexOfFirst { it.first == selectedFilter }.takeIf { it >= 0 }
            ?: 0,
        edgePadding = 16.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = {}
    ) {
        filters.forEachIndexed { index, (status, label) ->
            val selected = status == selectedFilter

            Tab(
                selected = selected,
                onClick = {
                    status.let { onEvent.invoke(FeedbackEvent.SelectedFilterChange(it)) }
                },
                text = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    }
}
