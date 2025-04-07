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
package com.newton.admin.presentation.events.view.management.composables.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.newton.commonUi.composables.animation.customAnimations.ShimmerWithFade

@Composable
fun AdminEventCardShimmer() {
    Box(
        modifier = Modifier
            .height(100.dp)
            .padding(horizontal = 10.dp)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Green, Color.Blue)),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
            ShimmerWithFade(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            ) {
                Box(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.1f
                        )
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(.6f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShimmerWithFade(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(10.dp)
                ) {
                    Box(
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.1f
                            )
                        )
                    )
                }
                ShimmerWithFade(
                    modifier = Modifier
                        .fillMaxWidth(.6f)
                        .height(10.dp)
                ) {
                    Box(
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.1f
                            )
                        )
                    )
                }
                ShimmerWithFade(
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .height(10.dp)
                ) {
                    Box(
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.1f
                            )
                        )
                    )
                }
            }
        }
    }
}
