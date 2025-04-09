package com.newton.account.presentation.composables.account

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.newton.commonUi.ui.CustomDialog

@Composable
fun ConfirmLogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
    ) {
        CustomDialog(
            title = "Confirm Account Logout",
            isErrorDialog = true,
            onDismiss = onDismiss,
            confirmButtonText = "Yes, Logout",
            onConfirm = onConfirm
        ) {
            Text(
                text = "Are you sure you want to log out? Any unsaved changes will be lost. All locally saved data will be deleted",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }