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

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.commonUi.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySheet(onEvent: (AddEventEvents) -> Unit, viewModel: AddEventViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onEvent.invoke(AddEventEvents.Sheet(shown = false)) }
    ) {
        ColumnWrapper(modifier = Modifier.heightIn(max = 640.dp)) {
            Text(
                "Select Category",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            SelectCategoriesContentView(
                onEvent = onEvent,
                categories = viewModel.categories
            )
            Spacer(Modifier.size(24.dp))
        }
    }
}
