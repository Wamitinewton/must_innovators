package com.newton.admin.presentation.events.view.management.composables.overview

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.animation.customAnimations.*

@Composable
fun AdminEventCardShimmer() {
    Box(
        modifier =
        Modifier
            .height(100.dp)
            .padding(horizontal = 10.dp)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Green, Color.Blue)),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            ShimmerWithFade(
                modifier =
                Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            ) {
                Box(
                    modifier =
                    Modifier.background(
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
                    modifier =
                    Modifier
                        .fillMaxWidth(.8f)
                        .height(10.dp)
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
                        .fillMaxWidth(.6f)
                        .height(10.dp)
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
                        .fillMaxWidth(.4f)
                        .height(10.dp)
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
