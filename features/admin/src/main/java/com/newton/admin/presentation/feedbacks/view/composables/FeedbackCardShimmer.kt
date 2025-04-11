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
package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.animations.*
import com.newton.commonUi.ui.*

@Composable
fun FeedbackCardShimmer(modifier: Modifier = Modifier) {
    val cardScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec =
        tween(
            durationMillis = 300,
            easing = EaseOutBack
        )
    )
    val cardAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 300)
    )
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(8) {
            CustomCard(
                modifier =
                modifier
                    .fillMaxWidth()
                    .scale(cardScale)
                    .alpha(cardAlpha)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                border =
                BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShimmerWithFade(
                            modifier =
                            Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                        ) {
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            content = {
                                ShimmerWithFade(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(20.dp)
                                ) {
                                    Box(
                                        modifier =
                                        Modifier
                                            .fillMaxSize()
                                            .background(Color(0xFF1A1A1A))
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Surface(
                            shape = RoundedCornerShape(20),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            content = {
                                ShimmerWithFade(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(20.dp)
                                ) {
                                    Box(
                                        modifier =
                                        Modifier
                                            .fillMaxSize()
                                            .background(Color(0xFF1A1A1A))
                                    )
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShimmerWithFade(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        ) {
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            ShimmerWithFade(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(20.dp)
                            ) {
                                Box(
                                    modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A))
                                )
                            }
                            ShimmerWithFade(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(20.dp)
                            ) {
                                Box(
                                    modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        Surface(
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.height(30.dp)
                        ) {
                            ShimmerWithFade(modifier = Modifier.width(80.dp)) {
                                Box(
                                    modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A))
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ShimmerWithFade(modifier = Modifier.height(80.dp)) {
                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .background(Color(0xFF1A1A1A))
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ShimmerWithFade(
                            modifier = modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(20))
                        ) {
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        ShimmerWithFade(
                            modifier = modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(20))
                        ) {
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        ShimmerWithFade(
                            modifier = modifier
                                .height(36.dp)
                                .clip(RoundedCornerShape(20))
                        ) {
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ShimmerWithFade(
                        modifier = modifier
                            .height(20.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(20))
                    ) {
                        Box(
                            modifier =
                            Modifier
                                .fillMaxSize()
                                .background(Color(0xFF1A1A1A))
                        )
                    }
                }
            }
        }
    }
}
