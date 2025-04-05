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
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.events.events.*
import com.newton.commonUi.ui.*

@Composable
fun SelectImageButton(onEvent: (AddEventEvents) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TakeImageButtonView(
            onClick = {
                onEvent.invoke(AddEventEvents.PickImage)
            }
        )
    }
}

@Composable
fun RowScope.TakeImageButtonView(onClick: () -> Unit) {
    CustomOutlinedButton(
        modifier = Modifier.weight(1f),
        contentPadding = PaddingValues(16.dp),
        onClick = onClick,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Select Poster Image", style = MaterialTheme.typography.titleMedium)
                Icon(imageVector = Icons.Outlined.Image, contentDescription = null)
            }
        }
    )
}
