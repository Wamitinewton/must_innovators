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

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*

@Composable
fun RatingBarInput(
    modifier: Modifier = Modifier,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            val isSelected = i <= currentRating
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsPressedAsState()

            val iconAlpha by animateFloatAsState(
                targetValue =
                    when {
                        isSelected -> 1f
                        isHovered -> 0.7f
                        else -> 0.5f
                    },
                animationSpec =
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                label = "starAlpha$i"
            )

            val iconScale by animateFloatAsState(
                targetValue =
                    when {
                        isSelected -> 1.2f
                        isHovered -> 1.1f
                        else -> 1.0f
                    },
                animationSpec =
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                label = "starScale$i"
            )

            Box(
                modifier =
                    Modifier
                        .size(48.dp)
                        .scale(iconScale)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onRatingChanged(i) }
                        ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isSelected) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                    contentDescription = "Star $i",
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = iconAlpha),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    maxRating: Int = 5,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(maxRating) { index ->
            Icon(
                imageVector = if (index < rating) Icons.Filled.StarRate else Icons.Outlined.StarRate,
                contentDescription = null,
                tint =
                    if (index < rating) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    },
                modifier =
                    Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
            )
        }

        Text(
            text = "$rating/$maxRating",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
