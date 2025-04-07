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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.animation.customAnimations.*

@Composable
fun OverviewTabShimmer() {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    List(4) {
                        ShimmerWithFade(
                            modifier =
                            Modifier
                                .height(100.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(10.dp))
                        ) {
                            Box(
                                modifier =
                                Modifier.background(
                                    MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = .3f
                                    )
                                )
                            )
                        }
                    }
                }
            }
            item {
                Text(
                    text = "Upcoming Events",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(3) {
                AdminEventCardShimmer()
            }
            item {
                Text(
                    text = "Past Events",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            items(2) {
                AdminEventCardShimmer()
            }
        }
    }
}
