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
package com.newton.account.presentation.composables.account

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.newton.commonUi.ui.*

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
