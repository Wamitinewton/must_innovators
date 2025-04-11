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
package com.newton.events.presentation.view.eventList

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.animations.*

@Composable
fun EventCardShimmer(
    staggerDelay: Int = 100,
    index: Int = 0
) {
    val shimmerColors =
        listOf(
            Color(0xFF303030),
            Color(0xFF454548),
            Color(0xFF454548)
        )

    val durationMillis = 1500 + (index * staggerDelay)

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp),
                    spotColor = Color(0x40000000)
                ),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ShimmerWithFade(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                colors = shimmerColors,
                durationMillis = durationMillis
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ShimmerWithFade(
                modifier =
                    Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp)),
                colors = shimmerColors,
                durationMillis = durationMillis
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            repeat(2) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ShimmerWithFade(
                        modifier =
                            Modifier
                                .size(20.dp)
                                .clip(CircleShape),
                        colors = shimmerColors,
                        durationMillis = durationMillis
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF1A1A1A))
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    ShimmerWithFade(
                        modifier =
                            Modifier
                                .width(if (it == 0) 100.dp else 120.dp)
                                .height(14.dp)
                                .clip(RoundedCornerShape(4.dp)),
                        colors = shimmerColors,
                        durationMillis = durationMillis
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF1A1A1A))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            ShimmerWithFade(
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(100.dp)
                        .height(36.dp)
                        .clip(RoundedCornerShape(16.dp)),
                colors = shimmerColors,
                durationMillis = durationMillis
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }
        }
    }
}

@Composable
fun EventShimmerList(count: Int = 3) {
    Column {
        repeat(count) { index ->
            EventCardShimmer(index = index)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
