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
            CustomDynamicAsyncImage(
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
