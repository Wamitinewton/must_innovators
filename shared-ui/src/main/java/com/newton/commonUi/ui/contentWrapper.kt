package com.newton.commonUi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

@Composable
fun RowWrapper(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement
        ) {
            content.invoke(this)
        }
    }
}

@Composable
fun ColumnWrapper(
    modifier: Modifier = Modifier,
    shapes: Shape = MaterialTheme.shapes.medium,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = shapes,
        shadowElevation = 2.dp,
        onClick = onClick
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .then(modifier),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content.invoke(this)
        }
    }
}
