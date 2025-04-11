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
