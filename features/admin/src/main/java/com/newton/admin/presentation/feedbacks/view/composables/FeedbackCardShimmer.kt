package com.newton.admin.presentation.feedbacks.view.composables

import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.custom_animations.ShimmerWithFade
import com.newton.common_ui.ui.CustomCard

@Composable
fun FeedbackCardShimmer(modifier: Modifier = Modifier) {
    val cardScale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = EaseOutBack
        )
    )
    val cardAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 300)
    )
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(8){
            CustomCard(
                modifier = modifier
                    .fillMaxWidth()
                    .scale(cardScale)
                    .alpha(cardAlpha)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                    ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShimmerWithFade(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            content = {
                                ShimmerWithFade(modifier = Modifier.width(100.dp).height(20.dp)) {
                                    Box(
                                        modifier = Modifier
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
                                ShimmerWithFade(modifier = Modifier.width(80.dp).height(20.dp)) {
                                    Box(
                                        modifier = Modifier
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
                        ShimmerWithFade(modifier = Modifier.size(40.dp).clip(CircleShape)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            ShimmerWithFade (modifier=Modifier.width(100.dp).height(20.dp)){
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A))
                                )
                            }
                            ShimmerWithFade (modifier=Modifier.width(100.dp).height(20.dp)){
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        Surface(
                            shape = RoundedCornerShape(50), modifier = Modifier.height(30.dp)
                        ) {
                            ShimmerWithFade(modifier = Modifier.width(80.dp)){
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF1A1A1A))
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ShimmerWithFade(modifier=Modifier.height(80.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF1A1A1A))
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ShimmerWithFade (modifier = modifier.height(36.dp).clip(RoundedCornerShape(20))){
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        ShimmerWithFade (modifier = modifier.height(36.dp).clip(RoundedCornerShape(20))){
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        ShimmerWithFade (modifier = modifier.height(36.dp).clip(RoundedCornerShape(20))){
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(Color(0xFF1A1A1A))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ShimmerWithFade (modifier = modifier.height(20.dp).width(100.dp).clip(RoundedCornerShape(20))){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF1A1A1A))
                        )
                    }
                }
            }
        }
    }

}