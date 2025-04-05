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
