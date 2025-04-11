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
package com.newton.admin.presentation.events.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.events.events.*
import com.newton.commonUi.ui.*

@Composable
fun SelectCategoriesContentView(
    categories: List<String>,
    onEvent: (AddEventEvents) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            CategoryItemView(
                category,
                onEvent = {
                    onEvent.invoke(AddEventEvents.ChangedCategory(category = category))
                    onEvent.invoke(AddEventEvents.Sheet(shown = false))
                }
            )
        }
    }
}

@Composable
fun CategoryItemView(
    category: String,
    onEvent: () -> Unit
) {
    RowWrapper(
        horizontalArrangement = Arrangement.Center,
        modifier =
        Modifier
            .background(
                MaterialTheme.colorScheme.onBackground.copy(0.3f)
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        onClick = { onEvent.invoke() }
    ) {
        Text(text = category, style = MaterialTheme.typography.titleMedium)
    }
}
