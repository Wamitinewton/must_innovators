package com.newton.admin.presentation.home.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

@Composable
fun AddChoiceCard(
    text: String,
    onclick: () -> Unit
) {
    Card(
        modifier =
        Modifier
            .height(50.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        onClick = onclick
    ) {
        Box(modifier = Modifier.padding(top = 5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color = Color.Cyan)
                )
                Spacer(Modifier.width(20.dp))
                Text(text, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
