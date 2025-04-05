package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.animation.customAnimations.*
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
