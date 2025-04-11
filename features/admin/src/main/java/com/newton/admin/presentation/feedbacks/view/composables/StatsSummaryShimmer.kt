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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.animations.*
import com.newton.commonUi.ui.*

@Composable
fun StatsSummaryShimmer(modifier: Modifier = Modifier) {
    CustomCard(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp)
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ShimmerWithFade(
                modifier =
                    Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                )
            }
            ShimmerWithFade(
                modifier =
                    Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                )
            }
            ShimmerWithFade(
                modifier =
                    Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                )
            }
            ShimmerWithFade(
                modifier =
                    Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
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
