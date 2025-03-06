package com.newton.events.presentation.view.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.custom_animations.ShimmerWithFade


@Composable
fun EventCardShimmer(
    staggerDelay: Int = 100,
    index: Int = 0
) {
    val shimmerColors = listOf(
        Color(0xFF303030),
        Color(0xFF454548),
        Color(0xFF656570),
        Color(0xFF454548),
        Color(0xFF303030)
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0x40000000)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ShimmerWithFade(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                colors = shimmerColors,
                durationMillis = 1500 + (index * staggerDelay)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ShimmerWithFade(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp)),
                colors = shimmerColors,
                durationMillis = 1500 + (index * staggerDelay)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                ShimmerWithFade(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    colors = shimmerColors,
                    durationMillis = 1500 + (index * staggerDelay)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                ShimmerWithFade(
                    modifier = Modifier
                        .width(40.dp)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    colors = shimmerColors,
                    durationMillis = 1500 + (index * staggerDelay)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            ShimmerWithFade(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp)),
                colors = shimmerColors,
                durationMillis = 1500 + (index * staggerDelay)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1A1A1A))
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                ShimmerWithFade(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape),
                    colors = shimmerColors,
                    durationMillis = 1500 + (index * staggerDelay)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                ShimmerWithFade(
                    modifier = Modifier
                        .width(100.dp)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    colors = shimmerColors,
                    durationMillis = 1500 + (index * staggerDelay)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                ShimmerWithFade(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape),
                    colors = shimmerColors,
                    durationMillis = 1500 + (index * staggerDelay)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                ShimmerWithFade(
                    modifier = Modifier
                        .width(120.dp)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    colors = shimmerColors,
                    durationMillis = 1500 + (index * staggerDelay)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF1A1A1A))
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ShimmerWithFade(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(100.dp)
                    .height(36.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = shimmerColors,
                durationMillis = 1500 + (index * staggerDelay)
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
fun EventCardShimmerList(
    itemCount: Int = 5,
    contentPadding: PaddingValues = PaddingValues(bottom = 16.dp)
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = contentPadding
    ) {
        itemsIndexed(List(itemCount) { it }) { index, _ ->
            EventCardShimmer(
                index = index,
                staggerDelay = 150
            )
        }
    }
}
