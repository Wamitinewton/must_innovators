package com.newton.admin.presentation.events.view.management.composables.overview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.animation.custom_animations.ShimmerWithFade

@Composable
fun OverviewTabShimmer() {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    List(4) {
                        ShimmerWithFade(
                            modifier = Modifier
                                .height(100.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(10.dp))
                        ) {
                            Box(
                                modifier = Modifier.background(
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