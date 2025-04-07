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
package com.newton.home.presentation.view

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import com.newton.commonUi.R

@Composable
fun AboutUsSectionCard(
    configuration: Configuration,
    onClick: () -> Unit
) {
    Card(
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .height(200.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.width((configuration.screenWidthDp * .4).dp)
                ) {
                    AsyncImage(
                        model = R.drawable.innovation,
                        contentDescription = "Innovators Image",
                        contentScale = ContentScale.Crop,
                        modifier =
                        Modifier
                            .fillMaxHeight()
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Club Bio", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        text = "Something",
                        maxLines = 7
                    )
                }
            }
        }
    }
}
