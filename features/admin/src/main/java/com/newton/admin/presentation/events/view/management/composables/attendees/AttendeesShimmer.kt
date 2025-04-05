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
package com.newton.admin.presentation.events.view.management.composables.attendees

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
import com.newton.commonUi.ui.*

@Composable
fun AttendeesShimmer(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ShimmerWithFade(
                        modifier =
                        Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                        )
                    }
                    ShimmerWithFade(
                        modifier =
                        Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                        )
                    }
                    ShimmerWithFade(
                        modifier =
                        Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                        )
                    }
                    ShimmerWithFade(
                        modifier =
                        Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                        )
                    }
                }
                Spacer(Modifier.height(2.dp))
                HorizontalDivider()
            }
            items(7) {
                CustomCard(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShimmerWithFade(
                            modifier =
                            Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                        ) {
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        shape = CircleShape
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            ShimmerWithFade(
                                modifier =
                                Modifier
                                    .fillMaxWidth(.6f)
                                    .height(10.dp)
                                    .clip(
                                        RoundedCornerShape(3.dp)
                                    )
                            ) {
                                Box(
                                    modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .3f)
                                        )
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            ShimmerWithFade(
                                modifier =
                                Modifier
                                    .fillMaxWidth(.4f)
                                    .height(10.dp)
                                    .clip(
                                        RoundedCornerShape(3.dp)
                                    )
                            ) {
                                Box(
                                    modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .3f)
                                        )
                                )
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            ShimmerWithFade(
                                modifier =
                                Modifier
                                    .fillMaxWidth(.3f)
                                    .height(26.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            ) {
                                Box(
                                    modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
