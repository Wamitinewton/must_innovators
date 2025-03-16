package com.newton.admin.presentation.events.view.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.newton.admin.presentation.events.events.AddEventEvents
import com.newton.admin.presentation.events.events.EventEvents
import com.newton.admin.presentation.events.states.AddEventState
import com.newton.common_ui.ui.CustomDynamicAsyncImage


@Composable
internal fun ImageReceiptView(state: AddEventState, onEvent: (AddEventEvents) -> Unit) {
    val uri = state.image
    uri?.let { safeUri ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(vertical = 10.dp)
        ) {
            CustomDynamicAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small),
                imageUrl = safeUri,
                contentDescription = "Receipt Image",
                contentScale = ContentScale.Crop
            )
            CloseButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                onDismiss = { onEvent.invoke(AddEventEvents.ClearImage)}
            )
        }
    }
}

@Composable
fun CloseButton(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
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
