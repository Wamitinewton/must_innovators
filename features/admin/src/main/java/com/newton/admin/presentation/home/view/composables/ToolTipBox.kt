package com.newton.admin.presentation.home.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.admin.*

@Composable
fun TooltipBox(
    tooltipData: ToolTipData,
    onDismiss: () -> Unit
) {
    Card(
        modifier =
        Modifier
            .offset(
                x = tooltipData.position.x.dp,
                y = tooltipData.position.y.dp
            )
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier =
            Modifier
                .padding(8.dp)
        ) {
            Text(
                text = tooltipData.title,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = tooltipData.value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
