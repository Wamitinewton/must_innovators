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
