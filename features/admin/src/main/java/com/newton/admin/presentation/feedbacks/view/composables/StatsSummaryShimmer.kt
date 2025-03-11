package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.custom_animations.ShimmerWithFade
import com.newton.common_ui.ui.CustomCard

@Composable
fun StatsSummaryShimmer(modifier: Modifier = Modifier) {
    CustomCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ShimmerWithFade(
                modifier = Modifier
                    .height(50.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }
            ShimmerWithFade(
                modifier = Modifier
                    .height(50.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }
            ShimmerWithFade(
                    modifier = Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                    ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }
            ShimmerWithFade(
                    modifier = Modifier
                        .height(50.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(10.dp))
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