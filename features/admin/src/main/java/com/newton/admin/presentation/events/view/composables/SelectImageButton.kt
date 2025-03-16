package com.newton.admin.presentation.events.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.common_ui.ui.CustomOutlinedButton


@Composable
fun SelectImageButton(onEvent: (AddEventEvents) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ){
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
