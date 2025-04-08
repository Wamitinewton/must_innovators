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
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import com.newton.admin.presentation.events.events.*
import com.newton.admin.presentation.events.states.*
import com.newton.commonUi.ui.*

@Composable
internal fun ImageReceiptView(
    state: AddEventState,
    onEvent: (AddEventEvents) -> Unit
) {
    val uri = state.image
    uri?.let { safeUri ->
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(vertical = 10.dp)
        ) {
            NetworkImage(
                modifier =
                Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small),
                imageUrl = safeUri,
                contentDescription = "Receipt Image",
                contentScale = ContentScale.Crop
            )
            CloseButton(
                modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                onDismiss = { onEvent.invoke(AddEventEvents.ClearImage) }
            )
        }
    }
}

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Surface(
        onClick = onDismiss,
        shape = CircleShape,
        modifier = modifier.size(32.dp),
        color = MaterialTheme.colorScheme.error
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = MaterialTheme.colorScheme.onError
        )
    }
}
