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
package com.newton.commonUi.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    initialValue: Long? = System.currentTimeMillis(),
    defaultValue: Long = System.currentTimeMillis(),
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = initialValue)
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            CustomTextButton(onClick = {
                val selectedDate = state.selectedDateMillis ?: defaultValue
                onConfirm.invoke(selectedDate)
            }) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            CustomTextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        CustomDatePicker(state)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomDatePicker(state: DatePickerState) {
    DatePicker(
        state = state,
        showModeToggle = false,
        title = null,
        headline = null,
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface)
    )
}
