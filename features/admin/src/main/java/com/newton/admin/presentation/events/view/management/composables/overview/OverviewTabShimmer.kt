package com.newton.admin.presentation.events.view.management.composables.overview

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

@Composable
fun OverviewTabShimmer() {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Row(
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    List(4) {
                        ShimmerWithFade(
                            modifier =
                            Modifier
                                .height(100.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(10.dp))
                        ) {
                            Box(
                                modifier =
                                Modifier.background(
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
