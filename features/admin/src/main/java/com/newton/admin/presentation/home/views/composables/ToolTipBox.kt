package com.newton.admin.presentation.home.views.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.admin.TooltipData

@Composable
fun TooltipBox(
    tooltipData: TooltipData,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .offset(
                x = tooltipData.position.x.dp,
                y = tooltipData.position.y.dp
            )
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
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